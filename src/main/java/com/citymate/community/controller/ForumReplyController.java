package com.citymate.community.controller;

import com.citymate.community.dto.CreateReplyRequest;
import com.citymate.community.dto.ForumReplyDTO;
import com.citymate.community.security.SecurityUtils;
import com.citymate.community.business.ForumReplyBusiness;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Path("/forum/discussions/{discussionId}/replies")
@RequiredArgsConstructor
public class ForumReplyController {

    private final ForumReplyBusiness service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReplies(@PathParam("discussionId") UUID discussionId) {
        List<ForumReplyDTO> replies = service.getRepliesByDiscussion(discussionId);
        return Response.ok(replies).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createReply(
            @PathParam("discussionId") UUID discussionId,
            @Valid CreateReplyRequest request
    ) {
        UUID authorId = SecurityUtils.getCurrentUserId();
        ForumReplyDTO created = service.createReply(discussionId, request, authorId);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }
}