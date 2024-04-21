package br.com.alura.service;

import br.com.alura.exception.CourseNotRegisteredException;
import br.com.alura.exception.UnprocessableEntityException;
import br.com.alura.exception.UserNotRegisteredException;
import br.com.alura.mapper.EnrollmentMapper;
import br.com.alura.model.dto.EnrollmentDto;
import br.com.alura.model.entity.Course;
import br.com.alura.model.entity.Enrollment;
import br.com.alura.model.entity.User;
import br.com.alura.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    private final CourseService courseService;
    
    private final UserService userService;

    private final EnrollmentMapper enrollmentMapper;

    public void createEnrollment(EnrollmentDto enrollmentDTO) {
        log.info("EnrollmentService.createEnrollment() -> init_process, enrollmentDTO {}", enrollmentDTO);
        String courseCode = enrollmentDTO.getCourseCode();

        Course course = courseService.findCourseOptionalByCode(courseCode)
                .orElseThrow(CourseNotRegisteredException::new);

        if (!course.isStatus()) {
            throw new UnprocessableEntityException("Course informed its not active.");
        }

        User user = userService.findUserOptionalByUserName(enrollmentDTO.getUserName())
                .orElseThrow(UserNotRegisteredException::new);

        Enrollment enrollment = enrollmentMapper.toEntity(enrollmentDTO);
        enrollment.setCourse(course);
        enrollment.setUser(user);

        enrollmentRepository.save(enrollment);

        log.info("EnrollmentService.createEnrollment() -> finish_process");
    }

    public List<Long> findCoursesThatHaveMoreThanFourEnrollments() {
        log.info("EnrollmentService.findCoursesThatHaveMoreThanFourEnrollments() -> init_process");
        return enrollmentRepository.findCourseIdsWithFourOrMoreEnrollments();
    }
}
