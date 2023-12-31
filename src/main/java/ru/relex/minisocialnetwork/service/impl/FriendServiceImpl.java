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
    public int createFriend(final String userEmail, final int friendId) {
        final UserEntity userEntity = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));
        if (!userRepository.existsById(friendId)) {
            throw new EntityNotFoundException("User not found!");
        }
        final FriendDto friendDto = FriendDto.builder().userId(userEntity.getId()).friendId(friendId).build();
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
    public void hideFriendsList(final String userEmail) {
        final UserEntity userEntity = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));
        userEntity.setHiddenFriendsList(true);
        userRepository.save(userEntity);
    }

    @Override
    public ViewListPage<FriendForListDto> getViewListPageOfFriends(final String userEmail, final String page, final String size) {
        UserEntity userEntity = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));
        return getViewListPage(userEntity.getId(), page, size);
    }

    @Override
    public ViewListPage<FriendForListDto> getViewListPageOfOtherUserFriends(final int userId, final String page, final String size) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));
        if (userEntity.isHiddenFriendsList()) {
            throw new EntityAccessDeniedException("Friends list was hidden by user!");
        }
        return getViewListPage(userEntity.getId(), page, size);
    }

    private ViewListPage<FriendForListDto> getViewListPage(final int userId, final String page, final String size) {
        final int pageNumber = Optional.ofNullable(page).map(ParseUtils::parsePositiveInteger).orElse(defaultPageNumber);
        final int pageSize = Optional.ofNullable(size).map(ParseUtils::parsePositiveInteger).orElse(defaultPageSize);

        final Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        final List<FriendForListDto> listFriends = listFriends(userId, pageable);
        final int totalAmount = numberOfFriends(userId);

        return getViewListPage(totalAmount, pageSize, pageNumber, listFriends);
    }

    @Override
    public List<FriendForListDto> listFriends(final int userId, final Pageable pageable) {
        final List<FriendEntity> friendEntities = friendRepository.findAllByUserId(userId, pageable).getContent();
        log.info("There have been found {} friends.", friendEntities.size());
        return friendMapper.friendEntitiesToFriendForListDtoList(friendEntities);
    }

    @Override
    public int numberOfFriends(final int userId) {
        final long numberOfFriends = friendRepository.countByUserId(userId);
        log.info("There have been found {} friends.", numberOfFriends);
        return (int) numberOfFriends;
    }

}
