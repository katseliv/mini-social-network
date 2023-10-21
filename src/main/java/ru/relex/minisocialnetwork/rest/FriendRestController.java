package ru.relex.minisocialnetwork.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.relex.minisocialnetwork.model.dto.view.FriendForListDto;
import ru.relex.minisocialnetwork.model.dto.view.ViewListPage;
import ru.relex.minisocialnetwork.service.FriendService;
import ru.relex.minisocialnetwork.utils.SecurityContextFacade;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/friends")
public class FriendRestController {

    private final FriendService friendService;
    private final SecurityContextFacade securityContextFacade;

    @PostMapping("/{friendId}")
    public ResponseEntity<Integer> createFriend(@PathVariable final int friendId) {
        final String userEmail = (String) securityContextFacade.getContext().getAuthentication().getPrincipal();
        final int id = friendService.createFriend(userEmail, friendId);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PutMapping("/hide")
    public ResponseEntity<String> hideFriendsList() {
        final String userEmail = (String) securityContextFacade.getContext().getAuthentication().getPrincipal();
        friendService.hideFriendsList(userEmail);
        return new ResponseEntity<>("Friends list was hidden!", HttpStatus.OK);
    }

    @GetMapping("/list")
    public ViewListPage<FriendForListDto> getFriends(@RequestParam(required = false) final Map<String, String> allParams) {
        final String userEmail = (String) securityContextFacade.getContext().getAuthentication().getPrincipal();
        return friendService.getViewListPageOfFriends(userEmail, allParams.get("page"), allParams.get("size"));
    }

    @GetMapping("/view/{userId}")
    public ViewListPage<FriendForListDto> getOtherUserFriends(@PathVariable final int userId, @RequestParam(required = false) final Map<String, String> allParams) {
        return friendService.getViewListPageOfOtherUserFriends(userId, allParams.get("page"), allParams.get("size"));
    }

}
