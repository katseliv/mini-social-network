package ru.relex.minisocialnetwork.service;

import ru.relex.minisocialnetwork.model.dto.UserDetailsDto;
import ru.relex.minisocialnetwork.model.response.JwtResponse;
import ru.relex.minisocialnetwork.model.response.LoginResponse;

import javax.security.auth.message.AuthException;

public interface AuthService {

    LoginResponse login(UserDetailsDto userDetailsDto);

    JwtResponse getNewAccessToken(String refreshToken) throws AuthException;

    void logout(String email);

}
