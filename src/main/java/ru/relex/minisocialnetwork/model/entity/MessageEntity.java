package ru.relex.minisocialnetwork.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity(name = "messages")
@Table(name = "messages")
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private UserEntity sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "id")
    private UserEntity receiver;

    @Column(name = "content")
    private String content;

    @Column(name = "sent_at")
    private Instant sentAt;

}
