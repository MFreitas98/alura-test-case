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
                .name("Java Advanced")
                .instructorId(1L)
                .code("java-adv")
                .active(true)
                .description("java course")
                .instructor(validUserDto)
                .build();
    }

    public static CourseDto getInvalidCourseDto() {

        return CourseDto.builder()
                .name("")
                .code("12345678901")
                .instructorId(null)
                .description("A course on Java programming")
                .build();
    }
}
