package ru.relex.minisocialnetwork.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;
import ru.relex.minisocialnetwork.annotation.PasswordMatch;
import ru.relex.minisocialnetwork.annotation.PasswordValid;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@PasswordMatch
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@JsonDeserialize(builder = UserPasswordDto.UserPasswordDtoBuilder.class)
public class UserPasswordDto {

    @NotNull(message = "Password is null.")
    @NotBlank(message = "Password is blank.")
    @PasswordValid
    private final String password;

    @NotNull(message = "Password Confirmation is null.")
    @NotBlank(message = "Password Confirmation is blank.")
    @PasswordValid
    private final String passwordConfirmation;

    @JsonPOJOBuilder(withPrefix = "")
    public static class UserPasswordDtoBuilder {

    }

}
