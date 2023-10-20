package ru.relex.minisocialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.relex.minisocialnetwork.exception.EntityAccessDeniedException;
import ru.relex.minisocialnetwork.exception.EntityCreationException;
import ru.relex.minisocialnetwork.exception.EntityNotFoundException;
import ru.relex.minisocialnetwork.mapper.FriendMapper;
import ru.relex.minisocialnetwork.model.FriendshipStatus;
import ru.relex.minisocialnetwork.model.dto.FriendDto;
import ru.relex.minisocialnetwork.model.dto.view.FriendForListDto;
import ru.relex.minisocialnetwork.model.dto.view.ViewListPage;
import ru.relex.minisocialnetwork.model.entity.FriendEntity;
import ru.relex.minisocialnetwork.model.entity.UserEntity;
import ru.relex.minisocialnetwork.repository.FriendRepository;
import ru.relex.minisocialnetwork.repository.UserRepository;
import ru.relex.minisocialnetwork.service.FriendService;
import ru.relex.minisocialnetwork.service.PaginationService;
import ru.relex.minisocialnetwork.utils.ParseUtils;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class FriendServiceImpl implements FriendService, PaginationService<FriendForListDto> {

    @Value("${pagination.defaultPageNumber}")
    private int defaultPageNumber;
    @Value("${pagination.defaultPageSize}")
    private int defaultPageSize;

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final FriendMapper friendMapper;

    @Override
    public int createFriend(int userId, int friendId) {
        if (!userRepository.existsById(userId) || !userRepository.existsById(friendId)) {
            throw new EntityNotFoundException("User not found!");
        }
        final FriendDto friendDto = FriendDto.builder().userId(userId).friendId(friendId).build();
        final FriendEntity friendEntity = Optional.of(friendDto)
                .map(friendMapper::friendDtoToFriendEntity)
                .map(friend -> {
                    friend.setStatus(FriendshipStatus.PENDING);
                    friend.setCreatedAt(Instant.now());
                    return friendRepository.save(friend);
                })
                .orElseThrow(() -> new EntityCreationException("Message not created!"));
        log.info("Message with id = {} has been created.", friendEntity.getId());
        return friendEntity.getId();
    }

    @Override
    public void hideFriendsList(int userId) {
        final UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));
        userEntity.setHiddenFriendsList(true);
        userRepository.save(userEntity);
    }

    @Override
    public ViewListPage<FriendForListDto> getViewListPage(int userId, String page, String size) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));
        if (userEntity.isHiddenFriendsList()) {
            throw new EntityAccessDeniedException("Friends list was hidden by user!");
        }
        final int pageNumber = Optional.ofNullable(page).map(ParseUtils::parsePositiveInteger).orElse(defaultPageNumber);
        final int pageSize = Optional.ofNullable(size).map(ParseUtils::parsePositiveInteger).orElse(defaultPageSize);

        final Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        final List<FriendForListDto> listFriends = listFriends(userId, pageable);
        final int totalAmount = numberOfFriends(userId);

        return getViewListPage(totalAmount, pageSize, pageNumber, listFriends);
    }

    @Override
    public List<FriendForListDto> listFriends(int userId, Pageable pageable) {
        final List<FriendEntity> friendEntities = friendRepository.findAllByUserIdOrFriendId(userId, userId, pageable).getContent();
        log.info("There have been found {} friends.", friendEntities.size());
        return friendMapper.friendEntitiesToFriendForListDtoList(friendEntities);
    }

    @Override
    public int numberOfFriends(int userId) {
        final long numberOfFriends = friendRepository.countByUserIdOrFriendId(userId, userId);
        log.info("There have been found {} friends.", numberOfFriends);
        return (int) numberOfFriends;
    }

}
