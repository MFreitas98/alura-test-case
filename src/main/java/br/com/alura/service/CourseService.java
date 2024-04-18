package br.com.alura.service;


import br.com.alura.exception.UnprocessableEntityException;
import br.com.alura.mapper.CourseMapper;
import br.com.alura.model.dto.CourseDto;
import br.com.alura.model.entity.Course;
import br.com.alura.model.entity.User;
import br.com.alura.model.enums.CourseStatusDelimiter;
import br.com.alura.model.enums.Role;
import br.com.alura.repository.CourseRepository;
import br.com.alura.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourseService {

    private final UserRepository userRepository;

    private final CourseMapper courseMapper;

    private final CourseRepository courseRepository;

    public void createCourse(CourseDto courseDto) {
        log.info("CourseService.createCourse() -> init_process, courseDto {} ", courseDto);
        User user = userRepository.findById(courseDto.getInstructorId()).orElseThrow(() ->
                new UnprocessableEntityException("Instructor informed is not an user."));

        validateInstructor(user.getRole());

        Course courseToBeCreated = courseMapper.toEntity(courseDto);
        courseToBeCreated.setInstructor(user);
        courseToBeCreated.setStatus(true);
        courseRepository.save(courseToBeCreated);
        log.info("CourseService.createCourse() -> finish_process");
    }

    private void validateInstructor(String role) {
        if (!Objects.equals(role, Role.INSTRUCTOR.name())) {
            throw new UnprocessableEntityException("Informed id was from an user that is not a instructor.Informed Role:" + role);
        }
    }

    public Page<CourseDto> listCoursesByStatus(CourseStatusDelimiter statusDelimiter, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Course> coursePage;

        switch (statusDelimiter) {
            case ACTIVE -> coursePage = courseRepository.findByStatus(true, pageable);
            case INACTIVE -> coursePage = courseRepository.findByStatus(false, pageable);
            default -> coursePage = courseRepository.findAll(pageable);
        }
        return coursePage.map(courseMapper::toDTO);
    }

}
