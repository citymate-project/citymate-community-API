package com.citymate.community.mapper;

import com.citymate.community.dto.ForumReplyDTO;
import com.citymate.community.entity.ForumReply;
import org.springframework.stereotype.Component;

@Component
public class ForumReplyMapper {

    public ForumReplyDTO toDTO(ForumReply entity) {
        return ForumReplyDTO.builder()
                .id(entity.getId())
                .discussionId(entity.getDiscussion().getId())
                .authorId(entity.getAuthorId())
                .content(entity.getContent())
                .quotedReplyId(entity.getQuotedReplyId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}