package ru.relex.minisocialnetwork.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.relex.minisocialnetwork.model.dto.UserDetailsDto;
import ru.relex.minisocialnetwork.model.dto.UserDto;
import ru.relex.minisocialnetwork.model.dto.UserPasswordDto;
import ru.relex.minisocialnetwork.model.dto.UserRegistrationDto;
import ru.relex.minisocialnetwork.model.dto.view.UserViewDto;
import ru.relex.minisocialnetwork.service.UserService;
import ru.relex.minisocialnetwork.utils.SecurityContextFacade;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {

    @Value("${authentication.defaultPrincipal}")
    private String defaultPrincipal;

    private final UserService userService;
    private final SecurityContextFacade securityContextFacade;

    @PostMapping("/register")
    public ResponseEntity<Integer> register(@RequestBody @Valid final UserRegistrationDto userRegistrationDto) {
        final int id = userService.createUser(userRegistrationDto);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public UserViewDto getUser(@PathVariable final int id) {
        return userService.getUserViewById(id);
    }

    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody @Valid final UserDto userDto) {
        final Authentication authentication = securityContextFacade.getContext().getAuthentication();
        if (authentication.getPrincipal().equals(defaultPrincipal)) {
            return new ResponseEntity<>("User wasn't updated due to unauthorized!", HttpStatus.FORBIDDEN);
        }
        final UserDetailsDto userDetailsDto = (UserDetailsDto) authentication.getPrincipal();
        final int id = userDetailsDto.getId();
        userService.updateUserById(id, userDto);
        return new ResponseEntity<>("User was updated!", HttpStatus.OK);
    }

    @PutMapping("/update-password")
    public ResponseEntity<String> updateUserPassword(@RequestBody @Valid final UserPasswordDto userPasswordDto) {
        final Authentication authentication = securityContextFacade.getContext().getAuthentication();
        if (authentication.getPrincipal().equals(defaultPrincipal)) {
            return new ResponseEntity<>("User's password wasn't updated due to unauthorized!", HttpStatus.FORBIDDEN);
        }
        final UserDetailsDto userDetailsDto = (UserDetailsDto) authentication.getPrincipal();
        final int id = userDetailsDto.getId();
        userService.updateUserPasswordById(id, userPasswordDto);
        return new ResponseEntity<>("User's password was updated!", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable final int id) {
        userService.deleteUserById(id);
    }

}
