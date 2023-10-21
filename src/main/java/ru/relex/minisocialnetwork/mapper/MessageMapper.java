package ru.relex.minisocialnetwork.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.relex.minisocialnetwork.model.dto.MessageDto;
import ru.relex.minisocialnetwork.model.dto.view.MessageForListDto;
import ru.relex.minisocialnetwork.model.entity.MessageEntity;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    MessageEntity messageDtoToMessageEntity(MessageDto dto);

    @Mapping(target = "sentAt", source = "sentAt", qualifiedByName = "stringDatetime")
    MessageForListDto messageEntityToMessageForListDto(MessageEntity entity);

    @Named(value = "stringDatetime")
    default String mapInstantToString(Instant datetime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss").withZone(ZoneId.systemDefault());
        return dateTimeFormatter.format(datetime);
    }

    List<MessageForListDto> messageEntitiesToMessageForListDtoList(Iterable<MessageEntity> messageEntities);

}
