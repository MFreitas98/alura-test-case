package br.com.alura.exception;

import lombok.ToString;
import lombok.experimental.StandardException;

@StandardException
@ToString
public class CourseNotRegisteredException extends RuntimeException {

    public CourseNotRegisteredException() {
        super("Course informed is not registered.");
    }
}
