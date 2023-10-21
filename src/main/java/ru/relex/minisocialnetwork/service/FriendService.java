package ru.relex.minisocialnetwork.service;

import org.springframework.data.domain.Pageable;
import ru.relex.minisocialnetwork.model.dto.view.FriendForListDto;
import ru.relex.minisocialnetwork.model.dto.view.ViewListPage;

import java.util.List;

public interface FriendService {

    int createFriend(String userEmail, int friendId);

    void hideFriendsList(String userEmail);

    ViewListPage<FriendForListDto> getViewListPageOfFriends(String userEmail, String page, String size);

    ViewListPage<FriendForListDto> getViewListPageOfOtherUserFriends(int userId, String page, String size);

    List<FriendForListDto> listFriends(int userId, Pageable pageable);

    int numberOfFriends(int userId);

}
