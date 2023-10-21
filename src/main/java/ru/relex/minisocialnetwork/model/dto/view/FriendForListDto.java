package ru.relex.minisocialnetwork.model.dto.view;

import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class FriendForListDto {

    private final String username;
    private final String firstName;
    private final String lastName;
    private final String status;

}
