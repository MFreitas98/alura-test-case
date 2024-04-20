package br.com.alura.arrange.dto;

import br.com.alura.model.dto.CourseDto;
import br.com.alura.model.dto.UserDto;
import br.com.alura.model.enums.Role;

public class CourseDtoArrange {

    private CourseDtoArrange() {

    }

    public static CourseDto getValidCourseDto() {

        UserDto validUserDto = UserDtoArrange.getValidUserDto();
        validUserDto.setRole(Role.INSTRUCTOR);

        return CourseDto.builder()
                .code("java-adv")
                .active(true)
                .description("java course")
                .instructor(validUserDto)
                .build();
    }
}
