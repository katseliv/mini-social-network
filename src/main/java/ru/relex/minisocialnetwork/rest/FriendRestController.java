package ru.relex.minisocialnetwork.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.relex.minisocialnetwork.model.dto.UserDetailsDto;
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
        final UserDetailsDto userDetailsDto = (UserDetailsDto) securityContextFacade.getContext().getAuthentication().getPrincipal();
        final int userId = userDetailsDto.getId();
        final int id = friendService.createFriend(userId, friendId);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PutMapping("/hide")
    public ResponseEntity<String> hideFriendsList() {
        final UserDetailsDto userDetailsDto = (UserDetailsDto) securityContextFacade.getContext().getAuthentication().getPrincipal();
        final int userId = userDetailsDto.getId();
        friendService.hideFriendsList(userId);
        return new ResponseEntity<>("Friends list was hidden!", HttpStatus.OK);
    }

    @GetMapping("/list")
    public ViewListPage<FriendForListDto> getFriends(@RequestParam(required = false) final Map<String, String> allParams) {
        final UserDetailsDto userDetailsDto = (UserDetailsDto) securityContextFacade.getContext().getAuthentication().getPrincipal();
        final int userId = userDetailsDto.getId();
        return friendService.getViewListPage(userId, allParams.get("page"), allParams.get("size"));
    }

    @GetMapping("/view/{userId}")
    public ViewListPage<FriendForListDto> getOtherUserFriends(@PathVariable final int userId, @RequestParam(required = false) final Map<String, String> allParams) {
        return friendService.getViewListPage(userId, allParams.get("page"), allParams.get("size"));
    }

}
