package com.citymate.community.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CreateNotificationRequest {

    @NotNull
    private UUID userId;

    @NotBlank
    private String type;

    @NotBlank
    private String title;

    private String message;
    private String link;
}