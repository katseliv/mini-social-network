package ru.relex.minisocialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.relex.minisocialnetwork.exception.EntityAlreadyExistsException;
import ru.relex.minisocialnetwork.exception.EntityCreationException;
import ru.relex.minisocialnetwork.exception.EntityNotFoundException;
import ru.relex.minisocialnetwork.mapper.UserMapper;
import ru.relex.minisocialnetwork.model.dto.UserDto;
import ru.relex.minisocialnetwork.model.dto.UserPasswordDto;
import ru.relex.minisocialnetwork.model.dto.UserRegistrationDto;
import ru.relex.minisocialnetwork.model.dto.view.UserViewDto;
import ru.relex.minisocialnetwork.model.entity.UserEntity;
import ru.relex.minisocialnetwork.repository.UserRepository;
import ru.relex.minisocialnetwork.service.UserService;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public int createUser(final UserRegistrationDto userRegistrationDto) {
        if (userRepository.findByEmail(userRegistrationDto.getEmail()).isPresent()) {
            log.warn("User with email = {} hasn't been created. Such user already exists!", userRegistrationDto.getEmail());
            throw new EntityAlreadyExistsException("User already exists in the database!");
        }
        final String password = passwordEncoder.encode(userRegistrationDto.getPassword());
        final UserEntity userEntity = Optional.of(userRegistrationDto)
                .map(user -> userMapper.userRegistrationDtoToUserEntityWithPassword(user, password))
                .map(user -> {
                    user.setAvatar(new byte[0]);
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new EntityCreationException("User not created!"));
        log.info("User with id = {} has been created.", userEntity.getId());
        return userEntity.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public UserViewDto getUserViewById(final int id) {
        final Optional<UserEntity> userEntity = userRepository.findById(id);
        userEntity.ifPresentOrElse(
                (user) -> log.info("User with id = {} has been found.", user.getId()),
                () -> log.warn("User with id = {} hasn't been found.", id));
        return userEntity.map(userMapper::userEntityToUserViewDto)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));
    }

    @Override
    @Transactional
    public void updateUserByEmail(final String email, final UserDto userDto) {
        final UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));

        final String username = userDto.getUsername();
        if (!Objects.equals(username, userEntity.getUsername()) && userRepository.existsByUsername(username)) {
            log.warn("User with username = {} hasn't been updated. Such username already exists in the database!", username);
            throw new EntityAlreadyExistsException("Such username already exists!");
        }

        if (userDto.getBase64StringAvatar().isEmpty()) {
            userMapper.mergeUserEntityAndUserDtoWithoutPicture(userEntity, userDto);
            userRepository.save(userEntity);
            log.info("User with email = {} has been updated without picture.", email);
        } else {
            userMapper.mergeUserEntityAndUserDto(userEntity, userDto);
            userRepository.save(userEntity);
            log.info("User with email = {} has been updated with picture.", email);
        }
    }

    @Override
    public void updateUserPasswordByEmail(final String email, final UserPasswordDto userPasswordDto) {
        final UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));
        final String password = passwordEncoder.encode(userPasswordDto.getPassword());
        userMapper.mergeUserEntityAndUserPasswordDto(userEntity, password);
        log.info("Password of user with email = {} has been updated.", email);
    }

    @Override
    @Transactional
    public void deleteUserByEmail(final String email) {
        final Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        userEntity.ifPresentOrElse(
                (user) -> log.info("User with email = {} has been found.", email),
                () -> {
                    log.warn("User with email = {} hasn't been found.", email);
                    throw new EntityNotFoundException("User not found!");
                });
        userRepository.deleteByEmail(email);
        log.info("User with email = {} has been deleted.", email);
    }

}
