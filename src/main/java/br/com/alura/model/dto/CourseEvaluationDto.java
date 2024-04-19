package br.com.alura.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseEvaluationDto {

    @NotBlank
    private String userName;

    @NotBlank
    private String courseCode;

    private String scoreReason;
}
