package ru.relex.minisocialnetwork.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;
import ru.relex.minisocialnetwork.annotation.EmailUnique;
import ru.relex.minisocialnetwork.annotation.PasswordMatch;
import ru.relex.minisocialnetwork.annotation.PasswordValid;
import ru.relex.minisocialnetwork.annotation.UsernameUnique;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@PasswordMatch
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@JsonDeserialize(builder = UserRegistrationDto.UserRegistrationDtoBuilder.class)
public class UserRegistrationDto {

    @NotNull(message = "Username is null.")
    @NotBlank(message = "Username is blank.")
    @UsernameUnique
    @Pattern(regexp = ".*([A-Z]|[a-z]|[А-Я]|[а-я]).*", message = "Username must contain a letter.")
    private final String username;

    @NotNull(message = "First Name is null.")
    @NotBlank(message = "First Name is blank.")
    @Pattern(regexp = "^([A-Z]|[a-z]|[А-Я]|[а-я])+$", message = "First Name mustn't contain a number.")
    private final String firstName;

    @NotNull(message = "Last Name is null.")
    @NotBlank(message = "Last Name is blank.")
    @Pattern(regexp = "^([A-Z]|[a-z]|[А-Я]|[а-я])+$", message = "Last Name mustn't contain a number.")
    private final String lastName;

    @NotNull(message = "Email is null.")
    @NotBlank(message = "Email is blank.")
    @Email(message = "Email invalid.")
    @EmailUnique
    private final String email;

    @NotNull(message = "Password is null.")
    @NotBlank(message = "Password is blank.")
    @PasswordValid
    private final String password;

    @NotNull(message = "Password Confirmation is null.")
    @NotBlank(message = "Password Confirmation is blank.")
    @PasswordValid
    private final String passwordConfirmation;

    @JsonPOJOBuilder(withPrefix = "")
    public static class UserRegistrationDtoBuilder {

    }

}
