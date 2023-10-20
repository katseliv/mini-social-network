package ru.relex.minisocialnetwork.model.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity(name = "users")
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "avatar")
    private byte[] avatar;

    @Column(name = "status")
    private String status;

    @Column(name = "bio")
    private String bio;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "hidden_friends_list")
    private boolean hiddenFriendsList;

}
