package com.citymate.community.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ForumReplyDTO {
    private UUID id;
    private UUID discussionId;
    private UUID authorId;
    private String content;
    private UUID quotedReplyId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}