package com.citymate.community.repository;

import com.citymate.community.entity.ForumReaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ForumReactionRepository extends JpaRepository<ForumReaction, UUID> {

    Optional<ForumReaction> findByTargetIdAndUserIdAndEmoji(UUID targetId, UUID userId, String emoji);
}