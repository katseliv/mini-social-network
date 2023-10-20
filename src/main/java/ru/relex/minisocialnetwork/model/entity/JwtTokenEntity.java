package ru.relex.minisocialnetwork.model.entity;

import lombok.*;
import ru.relex.minisocialnetwork.model.JwtTokenType;

import javax.persistence.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity(name = "jwt_tokens")
@Table(name = "jwt_tokens")
public class JwtTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "token")
    private String token;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private JwtTokenType type;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

}
