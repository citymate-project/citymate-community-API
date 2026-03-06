package com.citymate.community.controller;

import com.citymate.community.dto.ForumCategoryDTO;
import com.citymate.community.service.ForumCategoryService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Path("/forum/categories")
@RequiredArgsConstructor
public class ForumCategoryController {

    private final ForumCategoryService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCategories() {
        List<ForumCategoryDTO> categories = service.getAllCategories();
        return Response.ok(categories).build();
    }
}