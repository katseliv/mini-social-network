package ru.relex.minisocialnetwork.service;

import ru.relex.minisocialnetwork.model.JwtTokenType;
import ru.relex.minisocialnetwork.model.dto.JwtTokenDto;

public interface JwtTokenService {

    void createJwtToken(JwtTokenDto jwtTokenDto);

    String getJwtTokenByEmailAndType(String email, JwtTokenType type);

    boolean existsByToken(String token);

    boolean existsByUserEmail(String email);

    void updateJwtToken(JwtTokenDto jwtTokenDto);

    void deleteJwtTokensByEmail(String email);

}
