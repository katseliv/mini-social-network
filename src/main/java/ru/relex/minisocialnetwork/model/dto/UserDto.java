package ru.relex.minisocialnetwork.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;
import ru.relex.minisocialnetwork.annotation.ImageValid;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@JsonDeserialize(builder = UserDto.UserDtoBuilder.class)
public class UserDto {

    @NotNull(message = "Username is null.")
    @NotBlank(message = "Username is blank.")
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
    private final String email;

    @ImageValid
    private final String base64StringAvatar;

    @NotNull(message = "Status is null.")
    @NotBlank(message = "Status is blank.")
    private String status;

    @NotNull(message = "Bio is null.")
    @NotBlank(message = "Bio is blank.")
    private String bio;

    @JsonPOJOBuilder(withPrefix = "")
    public static class UserDtoBuilder {

    }

}
