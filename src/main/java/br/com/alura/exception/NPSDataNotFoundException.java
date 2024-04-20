package br.com.alura.exception;

import lombok.ToString;
import lombok.experimental.StandardException;

@StandardException
@ToString
public class NPSDataNotFoundException extends RuntimeException{

    public NPSDataNotFoundException() {
        super("There is not enough data to generateNPS.");
    }
}
