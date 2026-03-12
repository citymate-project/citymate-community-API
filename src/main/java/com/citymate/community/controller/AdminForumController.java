package com.citymate.community.controller;

import com.citymate.community.business.ForumCategoryBusiness;
import com.citymate.community.dto.CreateCategoryRequest;
import com.citymate.community.dto.ForumCategoryDTO;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Path("/admin/forum")
@RequiredArgsConstructor
public class AdminForumController {

    private final ForumCategoryBusiness forumCategoryBusiness;

    @POST
    @Path("/categories")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCategory(@Valid CreateCategoryRequest request) {
        ForumCategoryDTO created = forumCategoryBusiness.createCategory(request);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }
}