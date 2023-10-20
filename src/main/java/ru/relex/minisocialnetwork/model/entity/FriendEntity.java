package ru.relex.minisocialnetwork.model.entity;

import lombok.*;
import ru.relex.minisocialnetwork.model.FriendshipStatus;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity(name = "friends")
@Table(name = "friends")
public class FriendEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id1", referencedColumnName = "id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "user_id2", referencedColumnName = "id")
    private UserEntity friend;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private FriendshipStatus status;

    @Column(name = "created_at")
    private Instant createdAt;

}
