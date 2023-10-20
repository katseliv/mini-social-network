package ru.relex.minisocialnetwork.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@JsonDeserialize(builder = FriendDto.FriendDtoBuilder.class)
public class FriendDto {

    @NotNull(message = "User Id is null.")
    @Positive(message = "User Id is negative or zero.")
    private final Integer userId;

    @NotNull(message = "Friend Id is null.")
    @Positive(message = "Friend Id is negative or zero.")
    private final Integer friendId;

    @JsonPOJOBuilder(withPrefix = "")
    public static class FriendDtoBuilder {

    }

}
