package ru.relex.minisocialnetwork.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@JsonDeserialize(builder = MessageDto.MessageDtoBuilder.class)
public class MessageDto {

    @NotNull(message = "Receiver Username is null.")
    @NotBlank(message = "Receiver Username is blank.")
    private final String receiverUsername;

    @NotNull(message = "Content is null.")
    @NotBlank(message = "Content is blank.")
    private String content;

    @JsonPOJOBuilder(withPrefix = "")
    public static class MessageDtoBuilder {

    }

}
