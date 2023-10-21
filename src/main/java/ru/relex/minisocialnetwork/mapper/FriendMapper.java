package ru.relex.minisocialnetwork.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.relex.minisocialnetwork.model.dto.FriendDto;
import ru.relex.minisocialnetwork.model.dto.view.FriendForListDto;
import ru.relex.minisocialnetwork.model.entity.FriendEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FriendMapper {

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "friend.id", source = "friendId")
    FriendEntity friendDtoToFriendEntity(FriendDto friendDto);

    @Mapping(target = "username", source = "friend.username")
    @Mapping(target = "firstName", source = "friend.firstName")
    @Mapping(target = "lastName", source = "friend.lastName")
    FriendForListDto friendEntityToFriendForListDto(FriendEntity friendEntity);

    List<FriendForListDto> friendEntitiesToFriendForListDtoList(Iterable<FriendEntity> friendEntities);

}
