package com.citymate.community.service;

import com.citymate.community.dto.CreateDiscussionRequest;
import com.citymate.community.dto.ForumDiscussionDTO;
import com.citymate.community.dto.UpdateDiscussionRequest;
import com.citymate.community.entity.ForumCategory;
import com.citymate.community.entity.ForumDiscussion;
import com.citymate.community.mapper.ForumDiscussionMapper;
import com.citymate.community.repository.ForumCategoryRepository;
import com.citymate.community.repository.ForumDiscussionRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ForumDiscussionService {

    private final ForumDiscussionRepository discussionRepository;
    private final ForumCategoryRepository categoryRepository;
    private final ForumDiscussionMapper mapper;

    public List<ForumDiscussionDTO> getDiscussions(UUID categoryId, Boolean resolved, String search) {
        String categoryIdStr = categoryId != null ? categoryId.toString() : null;
        return discussionRepository.findWithFilters(categoryIdStr, resolved, search)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public ForumDiscussionDTO createDiscussion(CreateDiscussionRequest request, UUID authorId) {
        ForumCategory category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));

        ForumDiscussion discussion = ForumDiscussion.builder()
                .category(category)
                .authorId(authorId)
                .title(request.getTitle())
                .content(request.getContent())
                .isPinned(false)
                .isLocked(false)
                .isResolved(false)
                .viewsCount(0)
                .repliesCount(0)
                .build();

        return mapper.toDTO(discussionRepository.save(discussion));
    }
    public ForumDiscussionDTO getDiscussionById(UUID id) {
        ForumDiscussion discussion = discussionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Discussion not found"));
        return mapper.toDTO(discussion);
    }

    @Transactional
    public ForumDiscussionDTO updateDiscussion(UUID discussionId, UpdateDiscussionRequest request, UUID userId, String role) {
        ForumDiscussion discussion = discussionRepository.findById(discussionId)
                .orElseThrow(() -> new NotFoundException("Discussion not found"));

        boolean isAdmin = "ADMIN".equals(role);
        boolean isAuthor = discussion.getAuthorId().equals(userId);

        if (!isAdmin && !isAuthor) {
            throw new IllegalArgumentException("Access denied");
        }

        // Titre et contenu : auteur ou admin
        if (request.getTitle() != null) {
            discussion.setTitle(request.getTitle());
        }
        if (request.getContent() != null) {
            discussion.setContent(request.getContent());
        }

        // isResolved : auteur ou admin
        if (request.getIsResolved() != null) {
            discussion.setIsResolved(request.getIsResolved());
        }

        // isPinned et isLocked : admin seulement
        if (isAdmin) {
            if (request.getIsPinned() != null) {
                discussion.setIsPinned(request.getIsPinned());
            }
            if (request.getIsLocked() != null) {
                discussion.setIsLocked(request.getIsLocked());
            }
        }

        return mapper.toDTO(discussionRepository.save(discussion));
    }

    @Transactional
    public void deleteDiscussion(UUID discussionId, UUID userId, String role) {
        ForumDiscussion discussion = discussionRepository.findById(discussionId)
                .orElseThrow(() -> new NotFoundException("Discussion not found"));

        boolean isAdmin = "ADMIN".equals(role);
        boolean isAuthor = discussion.getAuthorId().equals(userId);

        if (!isAdmin && !isAuthor) {
            throw new IllegalArgumentException("Access denied");
        }

        discussionRepository.delete(discussion);
    }
}