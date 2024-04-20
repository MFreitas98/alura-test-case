package br.com.alura.arrange.entity;

import br.com.alura.model.entity.CourseEvaluation;
import br.com.alura.model.enums.ScoreValue;

public class CourseEvaluationEntityArrange {

    private CourseEvaluationEntityArrange() {

    }

    public static CourseEvaluation getValidCourseEvaluation() {

        return CourseEvaluation.builder()
                .id(1L)
                .user(UserEntityArrange.getValidUser())
                .course(CourseEntityArrange.getValidCourse())
                .score(ScoreValue.TEN.getValue())
                .scoreReason("Great course.")
                .build();
    }
}
