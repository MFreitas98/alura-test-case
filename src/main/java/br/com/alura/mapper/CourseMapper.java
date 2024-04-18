package br.com.alura.mapper;


import br.com.alura.model.dto.CourseDto;
import br.com.alura.model.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CourseMapper {

    Course toEntity(CourseDto courseDto);

}
