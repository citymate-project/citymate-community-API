package com.citymate.community.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CreateReplyRequest {

    @NotBlank
    @Size(min = 2)
    private String content;

    private UUID quotedReplyId; // optionnel
}