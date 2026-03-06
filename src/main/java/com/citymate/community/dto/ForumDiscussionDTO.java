package com.citymate.community.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ForumDiscussionDTO {
    private UUID id;
    private UUID categoryId;
    private UUID authorId;
    private String title;
    private String content;
    private Boolean isPinned;
    private Boolean isLocked;
    private Boolean isResolved;
    private Integer viewsCount;
    private Integer repliesCount;
    private LocalDateTime lastActivityAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}