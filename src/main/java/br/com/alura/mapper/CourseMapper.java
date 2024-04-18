package br.com.alura.mapper;


import br.com.alura.model.dto.CourseDto;
import br.com.alura.model.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CourseMapper {

    Course toEntity(CourseDto courseDto);

    @Mapping(target = "instructor.name", source = "course.instructor.name")
    @Mapping(target = "instructor.email", source = "course.instructor.email")
    @Mapping(target = "instructor.role", source = "course.instructor.role")
    @Mapping(target = "active", source = "course.status")
    @Mapping(target = "createdAt", source = "course.createdAt")
    @Mapping(target = "inactivatedAt", source = "course.inactivatedAt")
    CourseDto toDTO(Course course);

}
