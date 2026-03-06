package com.citymate.community.mapper;

import com.citymate.community.dto.ForumDiscussionDTO;
import com.citymate.community.entity.ForumDiscussion;
import org.springframework.stereotype.Component;

@Component
public class ForumDiscussionMapper {

    public ForumDiscussionDTO toDTO(ForumDiscussion entity) {
        return ForumDiscussionDTO.builder()
                .id(entity.getId())
                .categoryId(entity.getCategory().getId())
                .authorId(entity.getAuthorId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .isPinned(entity.getIsPinned())
                .isLocked(entity.getIsLocked())
                .isResolved(entity.getIsResolved())
                .viewsCount(entity.getViewsCount())
                .repliesCount(entity.getRepliesCount())
                .lastActivityAt(entity.getLastActivityAt())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}