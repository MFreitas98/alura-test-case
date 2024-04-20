package br.com.alura.service;

import br.com.alura.exception.CourseNotRegisteredException;
import br.com.alura.exception.NPSDataNotFoundException;
import br.com.alura.exception.UnprocessableEntityException;
import br.com.alura.exception.UserNotRegisteredException;
import br.com.alura.model.dto.CourseEvaluationDto;
import br.com.alura.model.dto.NetPromoterScoreDto;
import br.com.alura.model.entity.Course;
import br.com.alura.model.entity.CourseEvaluation;
import br.com.alura.model.entity.User;
import br.com.alura.model.enums.ScoreValue;
import br.com.alura.repository.CourseEvaluationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourseEvaluationService {

    private final CourseEvaluationRepository courseEvaluationRepository;

    private final UserService userService;

    private final CourseService courseService;

    private final EnrollmentService enrollmentService;

    public void createCourseEvaluation(CourseEvaluationDto courseEvaluationDto, ScoreValue score) {
        log.info("CourseEvaluationService.createCourseEvaluation() -> init_process , score {} , courseEvaluationDto {}",
                score, courseEvaluationDto);

        Course course = courseService.findCourseOptionalByCode(courseEvaluationDto.getCourseCode())
                .orElseThrow(CourseNotRegisteredException::new);

        if (!course.isStatus()) {
            throw new UnprocessableEntityException("Course informed in evaluate is not active.");
        }

        User user = userService.findUserOptionalByUserName(courseEvaluationDto.getUserName())
                .orElseThrow(UserNotRegisteredException::new);

        if (score.getValue() < 6) {
            EmailSenderService.send(course.getInstructor().getEmail(), user.getName(), score.getValue(), course.getName(),
                    course.getInstructor().getName());
        }

        CourseEvaluation courseEvaluation = CourseEvaluation.builder()
                .score(score.getValue())
                .scoreReason(courseEvaluationDto.getScoreReason())
                .user(user)
                .course(course)
                .build();
        courseEvaluationRepository.save(courseEvaluation);
        log.info("CourseEvaluationService.createCourseEvaluation() -> finish_process");
    }

    public List<NetPromoterScoreDto> generateNPS() {
        log.info("CourseEvaluationService.generateNPS() -> init_process");

        List<Long> coursesId = enrollmentService.findCoursesThatHaveMoreThanFourEnrollments();

        if (coursesId.isEmpty()) {
            throw new NPSDataNotFoundException();
        }

        List<NetPromoterScoreDto> netPromoterScoreList = new ArrayList<>();
        coursesId.forEach(courseId -> {

            List<CourseEvaluation> courseEvaluationsRelated =
                    courseEvaluationRepository.findCourseEvaluationByCourseId(courseId);

            long promoters = courseEvaluationsRelated.stream().filter(e -> e.getScore() >= 9).count();
            long neutrals = courseEvaluationsRelated.stream().filter(e -> e.getScore() >= 7 && e.getScore() <= 8).count();
            long detractors = courseEvaluationsRelated.stream().filter(e -> e.getScore() <= 6).count();

            long totalResponses = promoters + neutrals + detractors;

            double promoterPercentage = (double) promoters / totalResponses * 100;
            double detractorPercentage = (double) detractors / totalResponses * 100;

            double npsValue = BigDecimal.valueOf(promoterPercentage - detractorPercentage)
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();

            netPromoterScoreList.add(NetPromoterScoreDto.builder()
                    .courseCode(courseEvaluationsRelated.getFirst().getCourse().getCode())
                    .courseName(courseEvaluationsRelated.getFirst().getCourse().getName())
                    .score(npsValue)
                    .build());

        });
        log.info("CourseEvaluationService.generateNPS() -> finish_process, netPromoterScoreList {}", netPromoterScoreList);
        return netPromoterScoreList;
    }
}
