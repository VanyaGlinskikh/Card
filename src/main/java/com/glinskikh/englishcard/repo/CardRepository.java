package com.glinskikh.englishcard.repo;

import com.glinskikh.englishcard.model.Card;
import com.glinskikh.englishcard.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    @Query("SELECT c FROM Card c JOIN FETCH c.user WHERE c.user.id = :userId AND c.id = :cardId")
    Optional<Card> findByUserIdAndId(Long userId, Long cardId);

    @EntityGraph(attributePaths = "user")
    Page<Card> findByUserId(Long userId, Pageable pageable);

    @EntityGraph(attributePaths = "user")
    Page<Card> findAll(Pageable pageable);

}
