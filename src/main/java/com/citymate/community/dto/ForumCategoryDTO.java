package com.citymate.community.dto;

import lombok.*;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ForumCategoryDTO {
    private UUID id;
    private String name;
    private String slug;
    private String description;
    private String icon;
    private Integer orderIndex;
}