package ru.relex.minisocialnetwork.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;
import ru.relex.minisocialnetwork.model.JwtTokenType;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@JsonDeserialize(builder = JwtTokenDto.JwtTokenDtoBuilder.class)
public class JwtTokenDto {

    private final String token;
    private final JwtTokenType type;
    private final String email;

    @JsonPOJOBuilder(withPrefix = "")
    public static class JwtTokenDtoBuilder {

    }

}
