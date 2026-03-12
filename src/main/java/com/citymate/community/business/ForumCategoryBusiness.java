package com.citymate.community.business;

import com.citymate.community.dto.CreateCategoryRequest;
import com.citymate.community.dto.ForumCategoryDTO;
import com.citymate.community.entity.ForumCategory;
import com.citymate.community.mapper.ForumCategoryMapper;
import com.citymate.community.repository.ForumCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ForumCategoryBusiness {

    private final ForumCategoryRepository repository;
    private final ForumCategoryMapper mapper;

    public List<ForumCategoryDTO> getAllCategories() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public ForumCategoryDTO createCategory(CreateCategoryRequest request) {
        if (repository.existsBySlug(request.getSlug())) {
            throw new IllegalArgumentException("Ce slug existe déjà : " + request.getSlug());
        }

        ForumCategory category = ForumCategory.builder()
                .name(request.getName())
                .slug(request.getSlug())
                .description(request.getDescription())
                .icon(request.getIcon())
                .orderIndex(request.getOrderIndex())
                .build();

        return mapper.toDTO(repository.save(category));
    }
}