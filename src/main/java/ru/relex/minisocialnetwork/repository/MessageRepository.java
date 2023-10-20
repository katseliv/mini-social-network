package ru.relex.minisocialnetwork.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.relex.minisocialnetwork.model.entity.MessageEntity;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {

    Page<MessageEntity> findAllBySenderIdAndReceiverId(int senderId, int receiverId, Pageable pageable);

    long countBySenderIdAndReceiverId(int senderId, int receiverId);

}
