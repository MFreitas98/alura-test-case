package br.com.alura.service;

import br.com.alura.arrange.dto.CourseEvaluationDtoArrange;
import br.com.alura.arrange.entity.CourseEntityArrange;
import br.com.alura.arrange.entity.CourseEvaluationEntityArrange;
import br.com.alura.arrange.entity.UserEntityArrange;
import br.com.alura.exception.UnprocessableEntityException;
import br.com.alura.model.dto.CourseEvaluationDto;
import br.com.alura.model.dto.NetPromoterScoreDto;
import br.com.alura.model.entity.Course;
import br.com.alura.model.entity.CourseEvaluation;
import br.com.alura.model.entity.User;
import br.com.alura.model.enums.ScoreValue;
import br.com.alura.repository.CourseEvaluationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseEvaluationServiceTest {

    private final CourseEvaluationRepository courseEvaluationRepository = Mockito.mock(CourseEvaluationRepository.class);

    private final UserService userService = Mockito.mock(UserService.class);

    private final CourseService courseService = Mockito.mock(CourseService.class);

    private final EnrollmentService enrollmentService = Mockito.mock(EnrollmentService.class);

    private final CourseEvaluationService courseEvaluationService =
            new CourseEvaluationService(courseEvaluationRepository, userService, courseService, enrollmentService);

    @Test
    void should_throw_unprocessable_entity_exception_course_not_active() {
        CourseEvaluationDto courseEvaluationDto = CourseEvaluationDtoArrange.getValidCourseEvaluationDto();
        ScoreValue score = ScoreValue.EIGHT;
        String errorMessage = "Course informed in evaluate is not active.";

        Course course = CourseEntityArrange.getValidCourse();
        course.setStatus(false);

        when(courseService.findCourseOptionalByCode(courseEvaluationDto.getCourseCode())).thenReturn(java.util.Optional.of(course));

        UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class,
                () -> courseEvaluationService.createCourseEvaluation(courseEvaluationDto, score));
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void should_assert_by_console_email_sent_score_less_six() {
        CourseEvaluationDto courseEvaluationDto = CourseEvaluationDtoArrange.getValidCourseEvaluationDto();
        ScoreValue score = ScoreValue.FIVE;

        Course course = CourseEntityArrange.getValidCourse();
        User user = UserEntityArrange.getValidUser();

        when(courseService.findCourseOptionalByCode(courseEvaluationDto.getCourseCode())).thenReturn(java.util.Optional.of(course));
        when(userService.findUserOptionalByUserName(courseEvaluationDto.getUserName())).thenReturn(java.util.Optional.of(user));

        LoggerFactory.getLogger(CourseEvaluationService.class);
        ByteArrayOutputStream logOutputStream = new ByteArrayOutputStream();
        PrintStream logPrintStream = new PrintStream(logOutputStream);
        PrintStream originalLogStream = System.out;
        System.setOut(logPrintStream);

        courseEvaluationService.createCourseEvaluation(courseEvaluationDto, score);

        System.setOut(originalLogStream);

        String logOutput = logOutputStream.toString();
        assertTrue(logOutput.contains("Sending email to [" + course.getInstructor().getEmail() + "]"));
        assertTrue(logOutput.contains("Subject: Course Evaluation"));
        assertTrue(logOutput.contains("Body:"));
        assertTrue(logOutput.contains("The user " + user.getName() + " placed as a score " + score.getValue()
                + " in the course " + course.getName() + " for the instructor " + course.getInstructor().getName()));

        verify(courseEvaluationRepository, times(1)).save(any(CourseEvaluation.class));
    }

    @Test
    void should_creat_course_evaluation() {
        CourseEvaluationDto courseEvaluationDto = CourseEvaluationDtoArrange.getValidCourseEvaluationDto();
        ScoreValue score = ScoreValue.TEN;

        Course course = CourseEntityArrange.getValidCourse();
        User user = UserEntityArrange.getValidUser();

        when(courseService.findCourseOptionalByCode(anyString())).thenReturn(java.util.Optional.of(course));
        when(userService.findUserOptionalByUserName(anyString())).thenReturn(java.util.Optional.of(user));

        courseEvaluationService.createCourseEvaluation(courseEvaluationDto, score);

        verify(courseEvaluationRepository, times(1)).save(any(CourseEvaluation.class));
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0, 100, -100.0", // Bad NPS Score (100 detractors)
            "50, 0, 50, 0.0",     // Neutral NPS (50% promoters, 50% detractors) 0
            "75, 20, 5, 70.0",    // Very Good NPS (75% promoters, 20% neutrals, 5% detractors) 70
            "100, 0, 0, 100.0"    // Excellent NPS (100% promoters, no neutrals, no detractors) 100
    })
    void should_generateNPS_success_all_ranges_nps(int promoters, int neutrals, int detractors, double expectedNPS) {

        List<CourseEvaluation> courseEvaluations = new ArrayList<>();
        addCourseEvaluations(courseEvaluations, promoters, 9); // Pontuação 9 para promotores
        addCourseEvaluations(courseEvaluations, neutrals, 8); // Pontuação 8 para neutros
        addCourseEvaluations(courseEvaluations, detractors, 6); // Pontuação 6 para detratores

        when(enrollmentService.findCoursesThatHaveMoreThanFourEnrollments()).thenReturn(List.of(1L));
        when(courseEvaluationRepository.findCourseEvaluationByCourseId(anyLong())).thenReturn(courseEvaluations);

        List<NetPromoterScoreDto> result = courseEvaluationService.generateNPS();

        assertEquals(1, result.size());
        assertEquals(expectedNPS, result.getFirst().getScore(), 0.01);
    }

    @Test
    void should_throw_unprocessable_entity_exception_less_than_four_enrollments() {
        String errorMessage = "There is no Courses with more than four enrollments.";
        when(enrollmentService.findCoursesThatHaveMoreThanFourEnrollments()).thenReturn(List.of());

        UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class, courseEvaluationService::generateNPS);
        assertEquals(exception.getMessage(), errorMessage);
    }

    private void addCourseEvaluations(List<CourseEvaluation> evaluations, int count, int score) {
        for (int i = 0; i < count; i++) {
            CourseEvaluation evaluation = CourseEvaluationEntityArrange.getValidCourseEvaluation();
            evaluation.setScore(score);
            evaluations.add(evaluation);
        }
    }

}
