package ru.relex.minisocialnetwork.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.relex.minisocialnetwork.model.dto.JwtTokenDto;
import ru.relex.minisocialnetwork.model.entity.JwtTokenEntity;

@Mapper(componentModel = "spring")
public interface JwtTokenMapper {

    JwtTokenEntity jwtTokenDtoToJwtTokenEntity(JwtTokenDto dto);

    void mergeJwtTokenEntityAndJwtTokenDto(@MappingTarget JwtTokenEntity entity, JwtTokenDto dto);

}
