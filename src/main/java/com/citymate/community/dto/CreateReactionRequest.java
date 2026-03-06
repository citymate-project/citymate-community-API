package com.citymate.community.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CreateReactionRequest {

    @NotBlank
    private String emoji;
}