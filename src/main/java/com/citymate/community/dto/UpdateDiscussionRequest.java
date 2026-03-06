package com.citymate.community.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UpdateDiscussionRequest {
    private String title;
    private String content;
    private Boolean isPinned;    // admin seulement
    private Boolean isLocked;   // admin seulement
    private Boolean isResolved; // auteur ou admin
}