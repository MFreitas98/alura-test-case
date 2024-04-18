package br.com.alura.service;

import br.com.alura.exception.CourseNotRegisteredException;
import br.com.alura.exception.UnprocessableEntityException;
import br.com.alura.mapper.EnrollmentMapper;
import br.com.alura.model.dto.EnrollmentDTO;
import br.com.alura.model.entity.Course;
import br.com.alura.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    private final CourseService courseService;

    private final EnrollmentMapper enrollmentMapper;

    public void createEnrollment(EnrollmentDTO enrollmentDTO) {
        log.info("EnrollmentService.createEnrollment() -> init_process, enrollmentDTO {}", enrollmentDTO);
        String courseCode = enrollmentDTO.getCourseDto().getCode();

        Course course = courseService.findCourseOptionalByCode(courseCode)
                .orElseThrow(CourseNotRegisteredException::new);

        if (!course.isStatus()) {
            throw new UnprocessableEntityException("Course informed its not active.");
        }
        enrollmentRepository.save(enrollmentMapper.toEntity(enrollmentDTO));

        log.info("EnrollmentService.createEnrollment() -> finish_process");
    }
}
