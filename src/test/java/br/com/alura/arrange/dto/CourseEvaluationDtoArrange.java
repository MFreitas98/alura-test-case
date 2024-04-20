package br.com.alura.arrange.dto;

import br.com.alura.model.dto.CourseEvaluationDto;

public class CourseEvaluationDtoArrange {

    private CourseEvaluationDtoArrange() {

    }

    public static CourseEvaluationDto getValidCourseEvaluationDto() {

        return CourseEvaluationDto.builder()
                .courseCode("jpa-adv")
                .userName("test")
                .scoreReason("Great Course")
                .build();
    }
}
