package com.citymate.community.business;

import com.citymate.community.dto.CreateCategoryRequest;
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
    private ForumCategoryBusiness business;

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

        List<ForumCategoryDTO> result = business.getAllCategories();

        assertEquals(1, result.size());
        assertEquals("Logement", result.get(0).getName());
        verify(repository, times(1)).findAll();
    }

    @Test
    void getAllCategories_shouldReturnEmptyList_whenNoCategories() {
        when(repository.findAll()).thenReturn(List.of());

        List<ForumCategoryDTO> result = business.getAllCategories();

        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    void createCategory_shouldReturnDTO_whenSlugNotExists() {
        CreateCategoryRequest request = new CreateCategoryRequest();
        request.setName("Logement");
        request.setSlug("logement");

        ForumCategory saved = ForumCategory.builder()
                .id(UUID.randomUUID())
                .name("Logement")
                .slug("logement")
                .build();

        ForumCategoryDTO dto = ForumCategoryDTO.builder()
                .id(saved.getId())
                .name("Logement")
                .slug("logement")
                .build();

        when(repository.existsBySlug("logement")).thenReturn(false);
        when(repository.save(any())).thenReturn(saved);
        when(mapper.toDTO(saved)).thenReturn(dto);

        ForumCategoryDTO result = business.createCategory(request);

        assertNotNull(result);
        assertEquals("Logement", result.getName());
    }

    @Test
    void createCategory_shouldThrowException_whenSlugAlreadyExists() {
        CreateCategoryRequest request = new CreateCategoryRequest();
        request.setName("Logement");
        request.setSlug("logement");

        when(repository.existsBySlug("logement")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () ->
                business.createCategory(request)
        );

        verify(repository, never()).save(any());
    }
}
