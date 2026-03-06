package com.citymate.community.repository;

import com.citymate.community.entity.ForumReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ForumReplyRepository extends JpaRepository<ForumReply, UUID> {
    List<ForumReply> findByDiscussionIdOrderByCreatedAtAsc(UUID discussionId);
}