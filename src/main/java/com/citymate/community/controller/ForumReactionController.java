package com.citymate.community.controller;

import com.citymate.community.dto.CreateReactionRequest;
import com.citymate.community.dto.ForumReactionDTO;
import com.citymate.community.security.SecurityUtils;
import com.citymate.community.service.ForumReactionService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Path("/forum/discussions/{discussionId}/react")
@RequiredArgsConstructor
public class ForumReactionController {

    private final ForumReactionService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response react(
            @PathParam("discussionId") UUID discussionId,
            @Valid CreateReactionRequest request
    ) {
        UUID userId = SecurityUtils.getCurrentUserId();
        ForumReactionDTO reaction = service.toggleReaction(discussionId, request, userId);

        if (reaction == null) {
            // Réaction supprimée (toggle off)
            return Response.noContent().build();
        }

        return Response.status(Response.Status.CREATED).entity(reaction).build();
    }
}