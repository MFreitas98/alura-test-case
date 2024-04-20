package br.com.alura.service;

import br.com.alura.arrange.dto.CourseDtoArrange;
import br.com.alura.arrange.entity.CourseEntityArrange;
import br.com.alura.arrange.entity.UserEntityArrange;
import br.com.alura.exception.UnprocessableEntityException;
import br.com.alura.mapper.CourseMapper;
import br.com.alura.model.dto.CourseDto;
import br.com.alura.model.entity.Course;
import br.com.alura.model.entity.User;
import br.com.alura.model.enums.CourseStatusDelimiter;
import br.com.alura.model.enums.Role;
import br.com.alura.model.enums.StatusModifier;
import br.com.alura.repository.CourseRepository;
import br.com.alura.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);

    private final CourseMapper courseMapper = Mockito.mock(CourseMapper.class);

    private final CourseRepository courseRepository = Mockito.mock(CourseRepository.class);

    private final CourseService courseService = new CourseService(userRepository, courseMapper, courseRepository);

    private static final String COURSE_CODE = "java-adv";


    @Test
    void should_create_course() {

        CourseDto validCourseDto = CourseDtoArrange.getValidCourseDto();

        User validUser = UserEntityArrange.getValidUser();
        validUser.setRole(Role.INSTRUCTOR.name());

        when(userRepository.findById(validCourseDto.getInstructorId())).thenReturn(java.util.Optional.of(validUser));

        Course validCourse = CourseEntityArrange.getValidCourse();
        validCourse.setInstructor(validUser);

        when(courseMapper.toEntity(validCourseDto)).thenReturn(validCourse);

        courseService.createCourse(validCourseDto);

        verify(courseRepository, times(1)).save(validCourse);
    }

    @Test
    void should_throw_unprocessableEntityException_userNoTFound() {
        CourseDto validCourseDto = CourseDtoArrange.getValidCourseDto();
        String errorMessage = "Instructor informed is not an user.";
        when(userRepository.findById(validCourseDto.getInstructorId())).thenReturn(Optional.empty());

        UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class, () -> courseService.createCourse(validCourseDto));
        assertTrue(exception.getMessage().contains(errorMessage));
    }


    @Test
    void should_throw_unprocessableEntityException_instructorRole() {
        CourseDto validCourseDto = CourseDtoArrange.getValidCourseDto();
        String errorMessage = "Informed id was from an user that is not a instructor.Informed Role:";

        User user = UserEntityArrange.getValidUser();
        user.setRole(Role.STUDENT.toString());

        when(userRepository.findById(validCourseDto.getInstructorId())).thenReturn(Optional.of(user));

        UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class, () -> courseService.createCourse(validCourseDto));
        assertTrue(exception.getMessage().contains(errorMessage));
    }


    @Test
    void should_find_course_by_code() {

        Course validCourse = CourseEntityArrange.getValidCourse();
        CourseDto courseDto = CourseDtoArrange.getValidCourseDto();

        when(courseRepository.findByCode(COURSE_CODE)).thenReturn(Optional.of(validCourse));
        when(courseMapper.toDTO(validCourse)).thenReturn(courseDto);

        Optional<Course> courseOptionalByCode = courseService.findCourseOptionalByCode(COURSE_CODE);

        assertTrue(courseOptionalByCode.isPresent());
        assertEquals(courseOptionalByCode.get(), validCourse);
    }

    @ParameterizedTest
    @EnumSource(StatusModifier.class)
    void test_update_course_status(StatusModifier statusModifier) {

        courseService.updateCourseStatus(COURSE_CODE, statusModifier);

        if (StatusModifier.INACTIVATE.equals(statusModifier)) {
            verify(courseRepository, times(1)).updateCourse(eq(false), eq(COURSE_CODE), Mockito.any(OffsetDateTime.class));
        } else {
            verify(courseRepository, times(1)).updateCourse(eq(true), eq(COURSE_CODE), eq(null));
        }
    }

    @ParameterizedTest
    @EnumSource(CourseStatusDelimiter.class)
    void testListCoursesByStatus(CourseStatusDelimiter statusDelimiter) {
        int page = 0;
        int size = 10;

        Pageable pageable = PageRequest.of(page, size);
        Page<Course> coursePage = new PageImpl<>(Collections.emptyList());

        switch (statusDelimiter) {
            case ACTIVE:
                when(courseRepository.findByStatus(true, pageable)).thenReturn(coursePage);
                break;
            case INACTIVE:
                when(courseRepository.findByStatus(false, pageable)).thenReturn(coursePage);
                break;
            default:
                when(courseRepository.findAll(pageable)).thenReturn(coursePage);
                break;
        }

         courseService.listCoursesByStatus(statusDelimiter, page, size);

        switch (statusDelimiter) {
            case ACTIVE:
                verify(courseRepository, times(1)).findByStatus(true, pageable);
                break;
            case INACTIVE:
                verify(courseRepository, times(1)).findByStatus(false, pageable);
                break;
            default:
                verify(courseRepository, times(1)).findAll(pageable);
                break;
        }

        verify(courseMapper, times((int) coursePage.getTotalElements())).toDTO(any());
        coursePage.map(courseMapper::toDTO);
    }
}
