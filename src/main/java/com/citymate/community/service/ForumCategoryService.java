package com.citymate.community.service;

import com.citymate.community.dto.ForumCategoryDTO;
import com.citymate.community.mapper.ForumCategoryMapper;
import com.citymate.community.repository.ForumCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ForumCategoryService {

    private final ForumCategoryRepository repository;
    private final ForumCategoryMapper mapper;

    public List<ForumCategoryDTO> getAllCategories() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }
}