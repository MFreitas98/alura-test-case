package br.com.alura.arrange.dto;

import br.com.alura.model.dto.UserDto;
import br.com.alura.model.dto.UserDtoRequest;
import br.com.alura.model.enums.Role;

public class UserDtoArrange {

    private UserDtoArrange() {

    }

    public static UserDto getValidUserDto() {

        return UserDto.builder()
                .name("test")
                .email("test@gmail")
                .role(Role.STUDENT)
                .build();
    }

    public static UserDtoRequest getValidUserDtoRequest() {

        UserDtoRequest dtoRequest = new UserDtoRequest();
        dtoRequest.setName("test");
        dtoRequest.setUserName("admin");
        dtoRequest.setName("test");
        dtoRequest.setEmail("test@gmail");
        dtoRequest.setRole(Role.STUDENT);

        return dtoRequest;
    }
}
