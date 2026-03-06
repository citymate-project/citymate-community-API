package com.citymate.community.service;

import com.citymate.community.dto.CreateReactionRequest;
import com.citymate.community.dto.ForumReactionDTO;
import com.citymate.community.entity.ForumReaction;
import com.citymate.community.mapper.ForumReactionMapper;
import com.citymate.community.repository.ForumDiscussionRepository;
import com.citymate.community.repository.ForumReactionRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ForumReactionService {

    private final ForumReactionRepository reactionRepository;
    private final ForumDiscussionRepository discussionRepository;
    private final ForumReactionMapper mapper;

    @Transactional
    public ForumReactionDTO toggleReaction(UUID discussionId, CreateReactionRequest request, UUID userId) {

        // Vérifier que la discussion existe
        discussionRepository.findById(discussionId)
                .orElseThrow(() -> new NotFoundException("Discussion not found"));

        // Si la réaction existe déjà → on la supprime (toggle)
        Optional<ForumReaction> existing = reactionRepository
                .findByTargetIdAndUserIdAndEmoji(discussionId, userId, request.getEmoji());

        if (existing.isPresent()) {
            reactionRepository.delete(existing.get());
            return null; // réaction supprimée
        }

        // Sinon on la crée
        ForumReaction reaction = ForumReaction.builder()
                .targetType("discussion")
                .targetId(discussionId)
                .userId(userId)
                .emoji(request.getEmoji())
                .build();

        return mapper.toDTO(reactionRepository.save(reaction));
    }
}