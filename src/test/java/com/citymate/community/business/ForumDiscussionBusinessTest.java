package com.citymate.community.business;

import com.citymate.community.dto.CreateDiscussionRequest;
import com.citymate.community.dto.ForumDiscussionDTO;
import com.citymate.community.entity.ForumCategory;
import com.citymate.community.entity.ForumDiscussion;
import com.citymate.community.mapper.ForumDiscussionMapper;
import com.citymate.community.repository.ForumCategoryRepository;
import com.citymate.community.repository.ForumDiscussionRepository;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ForumDiscussionBusinessTest {

    @Mock
    private ForumDiscussionRepository discussionRepository;

    @Mock
    private ForumCategoryRepository categoryRepository;

    @Mock
    private ForumDiscussionMapper mapper;

    @InjectMocks
    private ForumDiscussionBusiness service;

    @Test
    void createDiscussion_shouldReturnDTO_whenCategoryExists() {
        UUID categoryId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();

        ForumCategory category = ForumCategory.builder()
                .id(categoryId).name("Logement").build();

        CreateDiscussionRequest request = new CreateDiscussionRequest();
        request.setCategoryId(categoryId);
        request.setTitle("Mon titre");
        request.setContent("Mon contenu de test");

        ForumDiscussion saved = ForumDiscussion.builder()
                .id(UUID.randomUUID())
                .category(category)
                .authorId(authorId)
                .title("Mon titre")
                .content("Mon contenu de test")
                .isPinned(false)
                .isLocked(false)
                .isResolved(false)
                .viewsCount(0)
                .repliesCount(0)
                .build();

        ForumDiscussionDTO dto = ForumDiscussionDTO.builder()
                .id(saved.getId())
                .title("Mon titre")
                .build();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(discussionRepository.save(any())).thenReturn(saved);
        when(mapper.toDTO(saved)).thenReturn(dto);

        ForumDiscussionDTO result = service.createDiscussion(request, authorId);

        assertNotNull(result);
        assertEquals("Mon titre", result.getTitle());
        verify(discussionRepository, times(1)).save(any());
    }

    @Test
    void createDiscussion_shouldThrowNotFoundException_whenCategoryNotFound() {
        UUID categoryId = UUID.randomUUID();

        CreateDiscussionRequest request = new CreateDiscussionRequest();
        request.setCategoryId(categoryId);
        request.setTitle("Titre");
        request.setContent("Contenu test");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
                service.createDiscussion(request, UUID.randomUUID())
        );

        verify(discussionRepository, never()).save(any());
    }

    @Test
    void getDiscussionById_shouldThrowNotFoundException_whenNotFound() {
        UUID id = UUID.randomUUID();
        when(discussionRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
                service.getDiscussionById(id)
        );
    }

    @Test
    void getDiscussionById_shouldReturnDTO_whenFound() {
        UUID id = UUID.randomUUID();
        ForumCategory category = ForumCategory.builder().id(UUID.randomUUID()).build();
        ForumDiscussion discussion = ForumDiscussion.builder()
                .id(id).category(category).title("Test").build();
        ForumDiscussionDTO dto = ForumDiscussionDTO.builder().id(id).title("Test").build();

        when(discussionRepository.findById(id)).thenReturn(Optional.of(discussion));
        when(mapper.toDTO(discussion)).thenReturn(dto);

        ForumDiscussionDTO result = service.getDiscussionById(id);

        assertNotNull(result);
        assertEquals("Test", result.getTitle());
    }

    @Test
    void deleteDiscussion_shouldSucceed_whenAuthor() {
        UUID discussionId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();

        ForumDiscussion discussion = ForumDiscussion.builder()
                .id(discussionId).authorId(authorId).build();

        when(discussionRepository.findById(discussionId)).thenReturn(Optional.of(discussion));

        assertDoesNotThrow(() ->
                service.deleteDiscussion(discussionId, authorId, "CLIENT")
        );

        verify(discussionRepository, times(1)).delete(discussion);
    }

    @Test
    void deleteDiscussion_shouldSucceed_whenAdmin() {
        UUID discussionId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        UUID adminId = UUID.randomUUID();

        ForumDiscussion discussion = ForumDiscussion.builder()
                .id(discussionId).authorId(authorId).build();

        when(discussionRepository.findById(discussionId)).thenReturn(Optional.of(discussion));

        assertDoesNotThrow(() ->
                service.deleteDiscussion(discussionId, adminId, "ADMIN")
        );

        verify(discussionRepository, times(1)).delete(discussion);
    }

    @Test
    void deleteDiscussion_shouldThrowAccessDenied_whenNotAuthorNorAdmin() {
        UUID discussionId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        UUID otherId = UUID.randomUUID();

        ForumDiscussion discussion = ForumDiscussion.builder()
                .id(discussionId).authorId(authorId).build();

        when(discussionRepository.findById(discussionId)).thenReturn(Optional.of(discussion));

        assertThrows(IllegalArgumentException.class, () ->
                service.deleteDiscussion(discussionId, otherId, "CLIENT")
        );

        verify(discussionRepository, never()).delete(any());
    }

    @Test
    void getDiscussions_shouldReturnList() {
        when(discussionRepository.findWithFilters(any(), any(), any()))
                .thenReturn(List.of());

        List<ForumDiscussionDTO> result = service.getDiscussions(null, null, null);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}