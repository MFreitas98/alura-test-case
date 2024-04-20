package br.com.alura.arrange.dto;

import br.com.alura.model.dto.EnrollmentDto;

public class EnrollmentDtoArrange {

    private EnrollmentDtoArrange() {

    }

    public static EnrollmentDto getValidEnrollmentDto() {

        return EnrollmentDto.builder()
                .courseCode("jpa-adv")
                .userName("test")
                .build();
    }
}
