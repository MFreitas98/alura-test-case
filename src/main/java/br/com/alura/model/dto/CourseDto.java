package br.com.alura.model.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
@Schema(description = "CourseDtO from Alura API.")
public class CourseDto implements Serializable {

    @NotBlank(message = "Course name should not be null or empty.")
    private String name;

    @Pattern(regexp = "^[a-zA-Z]{1,10}(?:-[a-zA-Z]+)?$",
            message = "The course code must be alphabetic, without spaces, numeric characters, or special characters, and have a maximum of 10 characters in length.")
    private String code;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long instructorId;

    private String description;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private boolean active;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private OffsetDateTime createdAt;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private OffsetDateTime inactivatedAt;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private UserDto instructor;
}
