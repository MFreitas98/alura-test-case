package br.com.alura.model.dto;

import br.com.alura.model.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {

    @NotBlank(message = "Name cannot be null or empty.")
    private String name;

    @Email(message = "E-mail format must be valid.")
    private String email;

    private Role role;
}