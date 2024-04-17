package br.com.alura.model.dto;

import br.com.alura.model.enums.Role;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.OffsetDateTime;


@Data
@Builder
@Value
public class UserDto implements Serializable {

    String name;
    @Pattern(regexp = "^[a-z]{1,20}$", message = "The username must have a maximum of 20 characters, be lowercase only, and contain no numbers or spaces.")
    String userName;
    @Email(message = "E-mail format must be valid.")
    String email;
    @NotNull
    Role role;
}