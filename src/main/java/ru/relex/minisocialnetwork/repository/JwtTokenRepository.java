package ru.relex.minisocialnetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.relex.minisocialnetwork.model.JwtTokenType;
import ru.relex.minisocialnetwork.model.entity.JwtTokenEntity;

import java.util.Optional;

@Repository
public interface JwtTokenRepository extends JpaRepository<JwtTokenEntity, Integer> {

    void deleteByUserEmailAndType(String email, JwtTokenType type);

    Optional<JwtTokenEntity> findByUserEmailAndType(String email, JwtTokenType type);

    boolean existsByToken(String token);

    boolean existsByUserEmailAndType(String email, JwtTokenType type);

}
