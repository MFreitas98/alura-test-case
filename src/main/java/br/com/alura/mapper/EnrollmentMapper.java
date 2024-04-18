package br.com.alura.mapper;

import br.com.alura.model.dto.EnrollmentDto;
import br.com.alura.model.entity.Enrollment;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EnrollmentMapper {

    Enrollment toEntity(EnrollmentDto enrollmentDTO);
}
