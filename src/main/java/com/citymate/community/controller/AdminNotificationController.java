package com.citymate.community.controller;

import com.citymate.community.dto.BroadcastNotificationRequest;
import com.citymate.community.service.NotificationService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Path("/admin/notifications")
@RequiredArgsConstructor
public class AdminNotificationController {

    private final NotificationService service;

    @POST
    @Path("/broadcast")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response broadcast(@Valid BroadcastNotificationRequest request) {
        // En production, on récupérerait la liste des users depuis la User API
        // Pour l'instant on accepte une liste d'IDs en paramètre query
        service.broadcast(request, List.of());
        return Response.ok().build();
    }
}