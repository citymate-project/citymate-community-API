package com.citymate.community.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BroadcastNotificationRequest {

    @NotBlank
    private String type;

    @NotBlank
    private String title;

    private String message;
    private String link;
}