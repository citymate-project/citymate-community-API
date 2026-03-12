package com.citymate.community.controller;

import com.citymate.community.dto.CreateNotificationRequest;
import com.citymate.community.dto.NotificationDTO;
import com.citymate.community.security.SecurityUtils;
import com.citymate.community.business.NotificationBusiness;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Path("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationBusiness service;

    // GET /api/notifications — récupérer ses notifications (client)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMyNotifications() {
        UUID userId = SecurityUtils.getCurrentUserId();
        List<NotificationDTO> notifications = service.getUserNotifications(userId);
        return Response.ok(notifications).build();
    }

    // PUT /api/notifications/read-all — marquer toutes comme lues (client)
    @PUT
    @Path("/read-all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response markAllAsRead() {
        UUID userId = SecurityUtils.getCurrentUserId();
        service.markAllAsRead(userId);
        return Response.ok().build();
    }

    // PUT /api/notifications/{id}/read — marquer une comme lue (client)
    @PUT
    @Path("/{id}/read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response markAsRead(@PathParam("id") UUID notificationId) {
        UUID userId = SecurityUtils.getCurrentUserId();
        NotificationDTO dto = service.markAsRead(notificationId, userId);
        return Response.ok(dto).build();
    }

    // POST /api/admin/notifications — créer une notification (admin)
    @POST
    @Path("/admin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNotification(@Valid CreateNotificationRequest request) {
        NotificationDTO created = service.createNotification(request);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }
}