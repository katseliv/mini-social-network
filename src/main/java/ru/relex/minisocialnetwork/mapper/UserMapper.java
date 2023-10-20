package ru.relex.minisocialnetwork.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import ru.relex.minisocialnetwork.model.dto.UserDetailsDto;
import ru.relex.minisocialnetwork.model.dto.UserDto;
import ru.relex.minisocialnetwork.model.dto.UserRegistrationDto;
import ru.relex.minisocialnetwork.model.dto.view.UserForListDto;
import ru.relex.minisocialnetwork.model.dto.view.UserViewDto;
import ru.relex.minisocialnetwork.model.entity.UserEntity;

import java.util.Base64;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "base64StringAvatar", source = "avatar", qualifiedByName = "bytesArrayAvatar")
    UserViewDto userEntityToUserViewDto(UserEntity entity);

    @Named(value = "bytesArrayAvatar")
    default String mapAvatar(byte[] avatar) {
        return Base64.getEncoder().encodeToString(avatar);
    }

    UserDetailsDto userEntityToUserDetailsDto(UserEntity entity);

    @Mapping(target = "password", source = "password")
    UserEntity userRegistrationDtoToUserEntityWithPassword(UserRegistrationDto dto, String password);

    @Mapping(target = "avatar", source = "base64StringAvatar", qualifiedByName = "base64StringAvatar")
    void mergeUserEntityAndUserDto(@MappingTarget UserEntity entity, UserDto dto);

    @Mapping(target = "password", source = "password")
    void mergeUserEntityAndUserPasswordDto(@MappingTarget UserEntity entity, String password);

    @Named(value = "base64StringAvatar")
    default byte[] mapBase64StringAvatar(String base64StringAvatar) {
        return Base64.getDecoder().decode(base64StringAvatar);
    }

    void mergeUserEntityAndUserDtoWithoutPicture(@MappingTarget UserEntity entity, UserDto dto);

    List<UserForListDto> userEntitiesToUserForListDtoList(Iterable<UserEntity> entities);

}
