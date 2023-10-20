package ru.relex.minisocialnetwork.service;

import org.springframework.data.domain.Pageable;
import ru.relex.minisocialnetwork.model.dto.MessageDto;
import ru.relex.minisocialnetwork.model.dto.view.MessageForListDto;
import ru.relex.minisocialnetwork.model.dto.view.ViewListPage;

import java.util.List;

public interface MessageService {

    int createMessage(int senderId, MessageDto messageDto);

    ViewListPage<MessageForListDto> getViewListPage(int senderId, int receiverId, String page, String size);

    List<MessageForListDto> listMessages(int senderId, int receiverId, Pageable pageable);

    int numberOfMessages(int senderId, int receiverId);

}
