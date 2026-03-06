package com.citymate.community.mapper;

import com.citymate.community.dto.ForumReactionDTO;
import com.citymate.community.entity.ForumReaction;
import org.springframework.stereotype.Component;

@Component
public class ForumReactionMapper {

    public ForumReactionDTO toDTO(ForumReaction entity) {
        return ForumReactionDTO.builder()
                .id(entity.getId())
                .targetType(entity.getTargetType())
                .targetId(entity.getTargetId())
                .userId(entity.getUserId())
                .emoji(entity.getEmoji())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}