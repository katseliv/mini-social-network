package ru.relex.minisocialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.relex.minisocialnetwork.exception.EntityCreationException;
import ru.relex.minisocialnetwork.exception.EntityNotFoundException;
import ru.relex.minisocialnetwork.mapper.MessageMapper;
import ru.relex.minisocialnetwork.model.dto.MessageDto;
import ru.relex.minisocialnetwork.model.dto.view.MessageForListDto;
import ru.relex.minisocialnetwork.model.dto.view.ViewListPage;
import ru.relex.minisocialnetwork.model.entity.MessageEntity;
import ru.relex.minisocialnetwork.model.entity.UserEntity;
import ru.relex.minisocialnetwork.repository.MessageRepository;
import ru.relex.minisocialnetwork.repository.UserRepository;
import ru.relex.minisocialnetwork.service.MessageService;
import ru.relex.minisocialnetwork.service.PaginationService;
import ru.relex.minisocialnetwork.utils.ParseUtils;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService, PaginationService<MessageForListDto> {

    @Value("${pagination.defaultPageNumber}")
    private int defaultPageNumber;
    @Value("${pagination.defaultPageSize}")
    private int defaultPageSize;

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final MessageMapper messageMapper;

    @Override
    public int createMessage(final String senderEmail, final MessageDto messageDto) {
        final UserEntity senderEntity = userRepository.findByEmail(senderEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));
        final UserEntity receiverEntity = userRepository.findByUsername(messageDto.getReceiverUsername())
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));
        final MessageEntity messageEntity = Optional.of(messageDto)
                .map(messageMapper::messageDtoToMessageEntity)
                .map(message -> {
                    message.setSender(senderEntity);
                    message.setReceiver(receiverEntity);
                    message.setSentAt(Instant.now());
                    return messageRepository.save(message);
                })
                .orElseThrow(() -> new EntityCreationException("Message not created!"));
        log.info("Message with id = {} has been created.", messageEntity.getId());
        return messageEntity.getId();
    }

    @Override
    public ViewListPage<MessageForListDto> getViewListPage(final String senderEmail, final int receiverId, final String page, final String size) {
        final int pageNumber = Optional.ofNullable(page).map(ParseUtils::parsePositiveInteger).orElse(defaultPageNumber);
        final int pageSize = Optional.ofNullable(size).map(ParseUtils::parsePositiveInteger).orElse(defaultPageSize);

        final UserEntity senderEntity = userRepository.findByEmail(senderEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));

        final Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        final List<MessageForListDto> listMessages = listMessages(senderEntity.getId(), receiverId, pageable);
        final int totalAmount = numberOfMessages(senderEntity.getId(), receiverId);

        return getViewListPage(totalAmount, pageSize, pageNumber, listMessages);
    }

    @Override
    public List<MessageForListDto> listMessages(final int senderId, final int receiverId, final Pageable pageable) {
        final List<MessageEntity> messageEntities = messageRepository.findAllBySenderIdAndReceiverId(senderId, receiverId, pageable).getContent();
        log.info("There have been found {} messages.", messageEntities.size());
        return messageMapper.messageEntitiesToMessageForListDtoList(messageEntities);
    }

    @Override
    public int numberOfMessages(final int senderId, final int receiverId) {
        final long numberOfMessages = messageRepository.countBySenderIdAndReceiverId(senderId, receiverId);
        log.info("There have been found {} messages.", numberOfMessages);
        return (int) numberOfMessages;
    }

}
