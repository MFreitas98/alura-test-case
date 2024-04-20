package br.com.alura.arrange.entity;

import br.com.alura.model.entity.Enrollment;

import java.time.OffsetDateTime;

public class EnrollmentEntityArrange {

    private EnrollmentEntityArrange() {

    }

    public static Enrollment getValidEnrollment() {

        return Enrollment.builder()
                .id(1L)
                .course(CourseEntityArrange.getValidCourse())
                .user(UserEntityArrange.getValidUser())
                .enrollmentDate(OffsetDateTime.now())
                .build();
    }
}
