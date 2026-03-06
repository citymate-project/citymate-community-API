package com.citymate.community.repository;

import com.citymate.community.entity.ForumDiscussion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ForumDiscussionRepository extends JpaRepository<ForumDiscussion, UUID> {

    @Query(value = """
        SELECT * FROM forum_discussions
        WHERE (:categoryId IS NULL OR category_id = CAST(:categoryId AS uuid))
        AND (:resolved IS NULL OR is_resolved = CAST(:resolved AS boolean))
        AND (:search IS NULL OR LOWER(title) LIKE LOWER(CONCAT('%', CAST(:search AS varchar), '%')))
        ORDER BY last_activity_at DESC
        """, nativeQuery = true)
    List<ForumDiscussion> findWithFilters(
            @Param("categoryId") String categoryId,
            @Param("resolved") Boolean resolved,
            @Param("search") String search
    );
}