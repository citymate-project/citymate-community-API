package com.citymate.community.business;

import com.citymate.community.dto.CreateReplyRequest;
import com.citymate.community.dto.ForumReplyDTO;
import com.citymate.community.entity.ForumDiscussion;
import com.citymate.community.entity.ForumReply;
import com.citymate.community.mapper.ForumReplyMapper;
import com.citymate.community.repository.ForumDiscussionRepository;
import com.citymate.community.repository.ForumReplyRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ForumReplyBusiness {

    private final ForumReplyRepository replyRepository;
    private final ForumDiscussionRepository discussionRepository;
    private final ForumReplyMapper mapper;

    public List<ForumReplyDTO> getRepliesByDiscussion(UUID discussionId) {
        return replyRepository.findByDiscussionIdOrderByCreatedAtAsc(discussionId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional
    public ForumReplyDTO createReply(UUID discussionId, CreateReplyRequest request, UUID authorId) {
        ForumDiscussion discussion = discussionRepository.findById(discussionId)
                .orElseThrow(() -> new NotFoundException("Discussion not found"));

        ForumReply reply = ForumReply.builder()
                .discussion(discussion)
                .authorId(authorId)
                .content(request.getContent())
                .quotedReplyId(request.getQuotedReplyId())
                .build();

        // Incrémenter le compteur de réponses
        discussion.setRepliesCount(discussion.getRepliesCount() + 1);
        discussionRepository.save(discussion);

        return mapper.toDTO(replyRepository.save(reply));
    }
}