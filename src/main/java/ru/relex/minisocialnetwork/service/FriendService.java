package ru.relex.minisocialnetwork.service;

import org.springframework.data.domain.Pageable;
import ru.relex.minisocialnetwork.model.dto.view.FriendForListDto;
import ru.relex.minisocialnetwork.model.dto.view.ViewListPage;

import java.util.List;

public interface FriendService {

    int createFriend(int userId, int friendId);

    void hideFriendsList(int userId);

    ViewListPage<FriendForListDto> getViewListPage(int userId, String page, String size);

    List<FriendForListDto> listFriends(int userId, Pageable pageable);

    int numberOfFriends(int userId);

}
