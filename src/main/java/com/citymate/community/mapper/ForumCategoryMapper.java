package com.citymate.community.mapper;

import com.citymate.community.dto.ForumCategoryDTO;
import com.citymate.community.entity.ForumCategory;
import org.springframework.stereotype.Component;

@Component
public class ForumCategoryMapper {

    public ForumCategoryDTO toDTO(ForumCategory entity) {
        return ForumCategoryDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .slug(entity.getSlug())
                .description(entity.getDescription())
                .icon(entity.getIcon())
                .orderIndex(entity.getOrderIndex())
                .build();
    }

    public ForumCategory toEntity(ForumCategoryDTO dto) {
        return ForumCategory.builder()
                .id(dto.getId())
                .name(dto.getName())
                .slug(dto.getSlug())
                .description(dto.getDescription())
                .icon(dto.getIcon())
                .orderIndex(dto.getOrderIndex())
                .build();
    }
}