package com.citymate.community.business;

import com.citymate.community.dto.ForumCategoryDTO;
import com.citymate.community.entity.ForumCategory;
import com.citymate.community.mapper.ForumCategoryMapper;
import com.citymate.community.repository.ForumCategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ForumCategoryBusinessTest {

    @Mock
    private ForumCategoryRepository repository;

    @Mock
    private ForumCategoryMapper mapper;

    @InjectMocks
    private ForumCategoryBusiness service;

    @Test
    void getAllCategories_shouldReturnListOfDTOs() {
        ForumCategory category = ForumCategory.builder()
                .id(UUID.randomUUID())
                .name("Logement")
                .slug("logement")
                .build();

        ForumCategoryDTO dto = ForumCategoryDTO.builder()
                .id(category.getId())
                .name("Logement")
                .slug("logement")
                .build();

        when(repository.findAll()).thenReturn(List.of(category));
        when(mapper.toDTO(category)).thenReturn(dto);

        List<ForumCategoryDTO> result = service.getAllCategories();

        assertEquals(1, result.size());
        assertEquals("Logement", result.get(0).getName());
        verify(repository, times(1)).findAll();
    }

    @Test
    void getAllCategories_shouldReturnEmptyList_whenNoCategories() {
        when(repository.findAll()).thenReturn(List.of());

        List<ForumCategoryDTO> result = service.getAllCategories();

        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();
    }
}