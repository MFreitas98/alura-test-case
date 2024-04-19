package br.com.alura.service;

import br.com.alura.exception.CourseNotRegisteredException;
import br.com.alura.exception.UnprocessableEntityException;
import br.com.alura.exception.UserNotRegisteredException;
import br.com.alura.model.dto.CourseEvaluationDto;
import br.com.alura.model.entity.Course;
import br.com.alura.model.entity.CourseEvaluation;
import br.com.alura.model.entity.User;
import br.com.alura.model.enums.ScoreValue;
import br.com.alura.repository.CourseEvaluationRepository;
import br.com.alura.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourseEvaluationService {

    private final CourseEvaluationRepository courseEvaluationRepository;

    private final UserService userService;

    private final CourseService courseService;

    public void createCourseEvaluation(CourseEvaluationDto courseEvaluationDto, ScoreValue score) {
        log.info("CourseEvaluationService.createCourseEvaluation() -> init_process , score {} , courseEvaluationDto {}",
                score, courseEvaluationDto);

        Course course = courseService.findCourseOptionalByCode(courseEvaluationDto.getCourseCode())
                .orElseThrow(CourseNotRegisteredException::new);

        if (!course.isStatus()) {
            throw new UnprocessableEntityException("Course informed its evaluate is not active.");
        }

        User user = userService.findUserOptionalByUserName(courseEvaluationDto.getUserName())
                .orElseThrow(UserNotRegisteredException::new);

        CourseEvaluation courseEvaluation = CourseEvaluation.builder()
                .score(score.getValue())
                .scoreReason(courseEvaluationDto.getScoreReason())
                .user(user)
                .course(course)
                .build();

        courseEvaluationRepository.save(courseEvaluation);
        log.info("CourseEvaluationService.createCourseEvaluation() -> finish_process");
    }
}
