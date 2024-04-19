package br.com.alura.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;



@Getter
@Setter
@ToString
@Schema(description = "Request to create an User on Alura API.")
public class UserDtoRequest extends UserDto {

    @Pattern(regexp = "^[a-z]{1,20}$", message = "The username must have a maximum of 20 characters, be lowercase only, and contain no numbers or spaces.")
    private String userName;

    @NotBlank
    private String password;
}