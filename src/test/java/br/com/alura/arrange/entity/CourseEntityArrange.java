package br.com.alura.arrange.entity;

import br.com.alura.model.entity.Course;

import java.time.OffsetDateTime;

public class CourseEntityArrange {

    private CourseEntityArrange() {

    }

    public static Course getValidCourse() {

        return Course.builder()
                .code("java-adv")
                .id(1L)
                .name("java adv")
                .description("java course")
                .instructor(UserEntityArrange.getValidUser())
                .createdAt(OffsetDateTime.now())
                .status(true)
                .build();

    }
}
