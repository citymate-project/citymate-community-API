package com.citymate.community.controller;

import com.citymate.community.dto.CreateDiscussionRequest;
import com.citymate.community.dto.ForumDiscussionDTO;
import com.citymate.community.dto.UpdateDiscussionRequest;
import com.citymate.community.security.SecurityUtils;
import com.citymate.community.business.ForumDiscussionBusiness;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Path("/forum/discussions")
@RequiredArgsConstructor
public class ForumDiscussionController {

    private final ForumDiscussionBusiness service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDiscussions(
            @QueryParam("categoryId") UUID categoryId,
            @QueryParam("resolved") Boolean resolved,
            @QueryParam("search") String search
    ) {
        List<ForumDiscussionDTO> discussions = service.getDiscussions(categoryId, resolved, search);
        return Response.ok(discussions).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createDiscussion(@Valid CreateDiscussionRequest request) {
        UUID authorId = SecurityUtils.getCurrentUserId();
        ForumDiscussionDTO created = service.createDiscussion(request, authorId);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDiscussion(@PathParam("id") UUID id) {
        ForumDiscussionDTO discussion = service.getDiscussionById(id);
        return Response.ok(discussion).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDiscussion(
            @PathParam("id") UUID id,
            UpdateDiscussionRequest request
    ) {
        UUID userId = SecurityUtils.getCurrentUserId();
        String role = SecurityUtils.getCurrentUserRole();
        ForumDiscussionDTO updated = service.updateDiscussion(id, request, userId, role);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDiscussion(@PathParam("id") UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        String role = SecurityUtils.getCurrentUserRole();
        service.deleteDiscussion(id, userId, role);
        return Response.noContent().build();
    }
}