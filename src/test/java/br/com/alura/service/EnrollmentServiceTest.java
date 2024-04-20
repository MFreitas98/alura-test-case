package br.com.alura.service;


import br.com.alura.arrange.dto.EnrollmentDtoArrange;
import br.com.alura.arrange.entity.CourseEntityArrange;
import br.com.alura.arrange.entity.EnrollmentEntityArrange;
import br.com.alura.arrange.entity.UserEntityArrange;
import br.com.alura.exception.CourseNotRegisteredException;
import br.com.alura.exception.UnprocessableEntityException;
import br.com.alura.exception.UserNotRegisteredException;
import br.com.alura.mapper.EnrollmentMapper;
import br.com.alura.model.dto.EnrollmentDto;
import br.com.alura.model.entity.Course;
import br.com.alura.model.entity.Enrollment;
import br.com.alura.model.entity.User;
import br.com.alura.repository.EnrollmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EnrollmentServiceTest {

    private final EnrollmentRepository enrollmentRepository = Mockito.mock(EnrollmentRepository .class);

    private final CourseService courseService = Mockito.mock(CourseService .class);

    private final UserService userService = Mockito.mock(UserService .class);

    private final EnrollmentMapper enrollmentMapper = Mockito.mock(EnrollmentMapper .class);

    private final EnrollmentService enrollmentService = new EnrollmentService(enrollmentRepository, courseService, userService, enrollmentMapper);

    @Test
    void should_create_enrollment() {
        EnrollmentDto enrollmentDto = EnrollmentDtoArrange.getValidEnrollmentDto();

        Course course = CourseEntityArrange.getValidCourse();
        User user = UserEntityArrange.getValidUser();
        Enrollment enrollment = EnrollmentEntityArrange.getValidEnrollment();

        when(courseService.findCourseOptionalByCode(enrollmentDto.getCourseCode())).thenReturn(Optional.of(course));
        when(userService.findUserOptionalByUserName(enrollmentDto.getUserName())).thenReturn(Optional.of(user));
        when(enrollmentMapper.toEntity(enrollmentDto)).thenReturn(enrollment);

        assertDoesNotThrow(() -> enrollmentService.createEnrollment(enrollmentDto));

        verify(courseService).findCourseOptionalByCode(enrollmentDto.getCourseCode());
        verify(userService).findUserOptionalByUserName(enrollmentDto.getUserName());
        verify(enrollmentRepository).save(enrollment);
    }

    @Test
    void testCreateEnrollment_CourseNotRegistered_Failure() {
        EnrollmentDto enrollmentDto = EnrollmentDtoArrange.getValidEnrollmentDto();
        String errorMessage ="Course informed is not registered.";

        when(courseService.findCourseOptionalByCode(enrollmentDto.getCourseCode())).thenReturn(Optional.empty());

        CourseNotRegisteredException exception = assertThrows(CourseNotRegisteredException.class, () -> enrollmentService.createEnrollment(enrollmentDto));
        assertEquals(exception.getMessage(), errorMessage);
    }

    @Test
    void testCreateEnrollment_UserNotRegistered_Failure() {
        EnrollmentDto enrollmentDto = EnrollmentDtoArrange.getValidEnrollmentDto();
        String errorMessage = "User informed is not registered.";

        Course course = CourseEntityArrange.getValidCourse();

        when(courseService.findCourseOptionalByCode(enrollmentDto.getCourseCode())).thenReturn(Optional.of(course));
        when(userService.findUserOptionalByUserName(enrollmentDto.getUserName())).thenReturn(Optional.empty());

        UserNotRegisteredException exception = assertThrows(UserNotRegisteredException.class, () -> enrollmentService.createEnrollment(enrollmentDto));
        assertEquals(exception.getMessage(), errorMessage);
    }

    @Test
    void testCreateEnrollment_CourseNotActive_Failure() {
        EnrollmentDto enrollmentDto = EnrollmentDtoArrange.getValidEnrollmentDto();
        String errorMessage = "Course informed its not active.";

        Course course = CourseEntityArrange.getValidCourse();
        course.setStatus(false);

        when(courseService.findCourseOptionalByCode(enrollmentDto.getCourseCode())).thenReturn(Optional.of(course));

        UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class, () -> enrollmentService.createEnrollment(enrollmentDto));
        assertEquals(exception.getMessage(), errorMessage);
    }

    @Test
    void findCoursesThatHaveMoreThanFourEnrollments_ReturnsCourseIds() {
        List<Long> expectedCourseIds = Arrays.asList(1L, 2L, 3L);
        when(enrollmentRepository.findCourseIdsWithFourOrMoreEnrollments()).thenReturn(expectedCourseIds);

        List<Long> actualCourseIds = enrollmentService.findCoursesThatHaveMoreThanFourEnrollments();

        assertEquals(expectedCourseIds, actualCourseIds);
    }
}
