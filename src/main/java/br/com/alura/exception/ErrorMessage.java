package br.com.alura.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {

    private Integer statusCode;

    private LocalDateTime timestamp;

    private String message;

    private String description;
}
