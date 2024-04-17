package br.com.alura.mapper;

import br.com.alura.model.dto.UserDto;
import br.com.alura.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {


    @Mapping(target = "role", source = "userDto.role.toString")
    User toEntity(UserDto userDto);
}
