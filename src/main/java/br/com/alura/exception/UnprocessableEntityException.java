package br.com.alura.exception;

import lombok.ToString;
import lombok.experimental.StandardException;

@StandardException
@ToString
public class UnprocessableEntityException extends RuntimeException{

    public UnprocessableEntityException(String message) {
        super(message);
    }
}
