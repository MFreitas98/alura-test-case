package br.com.alura.arrange.entity;

import br.com.alura.model.entity.User;
import br.com.alura.model.enums.Role;

public class UserEntityArrange {

    private UserEntityArrange() {

    }
    public static User getValidUser() {
        return User.builder()
                .id(1L)
                .name("teste")
                .userName("teste")
                .password("12345")
                .email("teste@gmail")
                .role(Role.STUDENT.toString())
                .build();
    }
}
