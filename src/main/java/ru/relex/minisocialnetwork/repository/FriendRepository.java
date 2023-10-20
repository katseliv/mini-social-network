package ru.relex.minisocialnetwork.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.relex.minisocialnetwork.model.entity.FriendEntity;

@Repository
public interface FriendRepository extends JpaRepository<FriendEntity, Integer> {

    Page<FriendEntity> findAllByUserIdOrFriendId(int userId, int friendId, Pageable pageable);

    long countByUserIdOrFriendId(int userId, int friendId);

}
