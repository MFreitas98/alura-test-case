package br.com.alura.exception;

import lombok.ToString;
import lombok.experimental.StandardException;

@StandardException
@ToString
public class UserNotRegisteredException extends RuntimeException {

    public UserNotRegisteredException() {
        super("User informed is not registered.");
    }
}
