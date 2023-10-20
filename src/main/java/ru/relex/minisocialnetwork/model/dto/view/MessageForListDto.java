package ru.relex.minisocialnetwork.model.dto.view;

import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class MessageForListDto {

    private final String content;
    private final String sentAt;

}
