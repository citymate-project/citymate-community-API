package com.citymate.community.business;

import com.citymate.community.dto.BroadcastNotificationRequest;
import com.citymate.community.dto.CreateNotificationRequest;
import com.citymate.community.dto.NotificationDTO;
import com.citymate.community.entity.Notification;
import com.citymate.community.mapper.NotificationMapper;
import com.citymate.community.repository.NotificationRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationBusiness {

    private final NotificationRepository repository;
    private final NotificationMapper mapper;

    public List<NotificationDTO> getUserNotifications(UUID userId) {
        return repository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public NotificationDTO createNotification(CreateNotificationRequest request) {
        Notification notification = Notification.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .title(request.getTitle())
                .message(request.getMessage())
                .link(request.getLink())
                .isRead(false)
                .build();

        return mapper.toDTO(repository.save(notification));
    }

    @Transactional
    public void markAllAsRead(UUID userId) {
        List<Notification> notifications = repository.findByUserIdOrderByCreatedAtDesc(userId);
        notifications.forEach(n -> n.setIsRead(true));
        repository.saveAll(notifications);
    }

    @Transactional
    public NotificationDTO markAsRead(UUID notificationId, UUID userId) {
        Notification notification = repository.findById(notificationId)
                .orElseThrow(() -> new NotFoundException("Notification not found"));

        if (!notification.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Access denied");
        }

        notification.setIsRead(true);
        return mapper.toDTO(repository.save(notification));
    }

    @Transactional
    public void broadcast(BroadcastNotificationRequest request, List<UUID> userIds) {
        List<Notification> notifications = userIds.stream()
                .map(userId -> Notification.builder()
                        .userId(userId)
                        .type(request.getType())
                        .title(request.getTitle())
                        .message(request.getMessage())
                        .link(request.getLink())
                        .isRead(false)
                        .build())
                .toList();

        repository.saveAll(notifications);
    }
}