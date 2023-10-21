package ru.relex.minisocialnetwork.service;

import ru.relex.minisocialnetwork.exception.EntityAlreadyExistsException;
import ru.relex.minisocialnetwork.model.dto.UserDto;
import ru.relex.minisocialnetwork.model.dto.UserPasswordDto;
import ru.relex.minisocialnetwork.model.dto.UserRegistrationDto;
import ru.relex.minisocialnetwork.model.dto.view.UserViewDto;

public interface UserService {

    int createUser(UserRegistrationDto userRegistrationDto) throws EntityAlreadyExistsException;

    UserViewDto getUserViewById(int id);

    void updateUserByEmail(String email, UserDto userDto);

    void updateUserPasswordByEmail(String email, UserPasswordDto userPasswordDto);

    void deleteUserByEmail(String email);

}
