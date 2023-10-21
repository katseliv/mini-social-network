package ru.relex.minisocialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.relex.minisocialnetwork.exception.EntityCreationException;
import ru.relex.minisocialnetwork.exception.EntityNotFoundException;
import ru.relex.minisocialnetwork.mapper.JwtTokenMapper;
import ru.relex.minisocialnetwork.model.JwtTokenType;
import ru.relex.minisocialnetwork.model.dto.JwtTokenDto;
import ru.relex.minisocialnetwork.model.entity.JwtTokenEntity;
import ru.relex.minisocialnetwork.model.entity.UserEntity;
import ru.relex.minisocialnetwork.repository.JwtTokenRepository;
import ru.relex.minisocialnetwork.repository.UserRepository;
import ru.relex.minisocialnetwork.service.JwtTokenService;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class JwtTokenServiceImpl implements JwtTokenService {

    private final JwtTokenRepository jwtTokenRepository;
    private final UserRepository userRepository;
    private final JwtTokenMapper jwtTokenMapper;

    @Override
    @Transactional
    public void createJwtToken(final JwtTokenDto jwtTokenDto) {
        final Optional<UserEntity> userEntity = userRepository.findByEmail(jwtTokenDto.getEmail());
        userEntity.ifPresentOrElse(
                (user) -> {
                    final JwtTokenEntity jwtTokenEntity = Optional.of(jwtTokenDto)
                            .map(jwtTokenMapper::jwtTokenDtoToJwtTokenEntity)
                            .map((jwtToken) -> {
                                jwtToken.setUser(user);
                                return jwtTokenRepository.save(jwtToken);
                            })
                            .orElseThrow(() -> new EntityCreationException("Token not created!"));
                    log.info("Token with type = {} for email = {} with id = {} has been created.",
                            jwtTokenDto.getType(), jwtTokenDto.getEmail(), jwtTokenEntity.getId());
                },
                () -> {
                    log.warn("Token with type = {} for email = {} hasn't been created.",
                            jwtTokenDto.getType(), jwtTokenDto.getEmail());
                    throw new EntityCreationException("Token not created!");
                }
        );
    }

    @Override
    @Transactional(readOnly = true)
    public String getJwtTokenByEmailAndType(final String email, final JwtTokenType type) {
        final Optional<JwtTokenEntity> jwtTokenEntity = jwtTokenRepository.findByUserEmailAndType(email, type);
        jwtTokenEntity.ifPresentOrElse(
                (token) -> log.info("Token with type = {} for email = {} with id = {} has been found.", type, email, token.getId()),
                () -> log.warn("Token with type = {} for email = {} hasn't been found.", type, email));
        return jwtTokenEntity.orElseThrow(() -> new EntityNotFoundException("Token not found!")).getToken();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByToken(final String token) {
        final boolean existsByToken = jwtTokenRepository.existsByToken(token);
        if (existsByToken) {
            log.info("Token exists.");
        } else {
            log.warn("Token doesn't exist.");
        }
        return existsByToken;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUserEmail(final String email) {
        final boolean existsByUserEmail = jwtTokenRepository.existsByUserEmailAndType(email, JwtTokenType.ACCESS)
                && jwtTokenRepository.existsByUserEmailAndType(email, JwtTokenType.REFRESH);
        if (existsByUserEmail) {
            log.info("Tokens for email = {} exist.", email);
        } else {
            log.warn("Tokens for email = {} don't exist.", email);
        }
        return existsByUserEmail;
    }

    @Override
    @Transactional
    public void updateJwtToken(final JwtTokenDto jwtTokenDto) {
        final JwtTokenEntity jwtTokenEntity = jwtTokenRepository.findByUserEmailAndType(
                jwtTokenDto.getEmail(), jwtTokenDto.getType()).orElseThrow(() -> {
            log.warn("Token with type = {} for email = {} doesn't exist", jwtTokenDto.getType(), jwtTokenDto.getEmail());
            throw new EntityCreationException("New access token hasn't been created!");
        });
        jwtTokenMapper.mergeJwtTokenEntityAndJwtTokenDto(jwtTokenEntity, jwtTokenDto);
        final JwtTokenEntity updatedJwtTokenEntity = jwtTokenRepository.save(jwtTokenEntity);
        log.info("Token with type = {} for email = {} with id = {} has been updated.",
                jwtTokenDto.getType(), jwtTokenDto.getEmail(), updatedJwtTokenEntity.getId());
    }

    @Override
    @Transactional
    public void deleteJwtTokensByEmail(final String email) {
        if (jwtTokenRepository.existsByUserEmailAndType(email, JwtTokenType.ACCESS)
                && jwtTokenRepository.existsByUserEmailAndType(email, JwtTokenType.REFRESH)) {
            log.info("Tokens for email = {} have been found.", email);
        } else {
            log.warn("Tokens for email = {} haven't been found.", email);
            throw new EntityNotFoundException("Tokens not found!");
        }
        jwtTokenRepository.deleteByUserEmailAndType(email, JwtTokenType.ACCESS);
        jwtTokenRepository.deleteByUserEmailAndType(email, JwtTokenType.REFRESH);
        log.info("Tokens for email = {} have been deleted.", email);
    }

}
