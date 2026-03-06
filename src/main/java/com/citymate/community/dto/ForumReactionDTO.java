package com.citymate.community.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ForumReactionDTO {
    private UUID id;
    private String targetType;
    private UUID targetId;
    private UUID userId;
    private String emoji;
    private LocalDateTime createdAt;
}