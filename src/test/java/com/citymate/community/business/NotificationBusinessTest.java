package com.citymate.community.business;

import com.citymate.community.dto.CreateNotificationRequest;
import com.citymate.community.dto.NotificationDTO;
import com.citymate.community.entity.Notification;
import com.citymate.community.mapper.NotificationMapper;
import com.citymate.community.repository.NotificationRepository;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationBusinessTest {

    @Mock
    private NotificationRepository repository;

    @Mock
    private NotificationMapper mapper;

    @InjectMocks
    private NotificationBusiness service;

    @Test
    void getUserNotifications_shouldReturnList() {
        UUID userId = UUID.randomUUID();
        Notification notif = Notification.builder()
                .id(UUID.randomUUID()).userId(userId)
                .title("Test").type("INFO").isRead(false).build();
        NotificationDTO dto = NotificationDTO.builder()
                .id(notif.getId()).title("Test").build();

        when(repository.findByUserIdOrderByCreatedAtDesc(userId)).thenReturn(List.of(notif));
        when(mapper.toDTO(notif)).thenReturn(dto);

        List<NotificationDTO> result = service.getUserNotifications(userId);

        assertEquals(1, result.size());
        assertEquals("Test", result.get(0).getTitle());
    }

    @Test
    void getUserNotifications_shouldReturnEmpty_whenNone() {
        UUID userId = UUID.randomUUID();
        when(repository.findByUserIdOrderByCreatedAtDesc(userId)).thenReturn(List.of());

        List<NotificationDTO> result = service.getUserNotifications(userId);

        assertTrue(result.isEmpty());
    }

    @Test
    void createNotification_shouldReturnDTO() {
        UUID userId = UUID.randomUUID();

        CreateNotificationRequest request = new CreateNotificationRequest();
        request.setUserId(userId);
        request.setType("INFO");
        request.setTitle("Bienvenue");

        Notification saved = Notification.builder()
                .id(UUID.randomUUID()).userId(userId)
                .type("INFO").title("Bienvenue").isRead(false).build();

        NotificationDTO dto = NotificationDTO.builder()
                .id(saved.getId()).title("Bienvenue").build();

        when(repository.save(any())).thenReturn(saved);
        when(mapper.toDTO(saved)).thenReturn(dto);

        NotificationDTO result = service.createNotification(request);

        assertNotNull(result);
        assertEquals("Bienvenue", result.getTitle());
        verify(repository, times(1)).save(any());
    }

    @Test
    void markAsRead_shouldThrowNotFoundException_whenNotFound() {
        UUID notifId = UUID.randomUUID();
        when(repository.findById(notifId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
                service.markAsRead(notifId, UUID.randomUUID())
        );
    }

    @Test
    void markAsRead_shouldThrowAccessDenied_whenWrongUser() {
        UUID notifId = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();

        Notification notif = Notification.builder()
                .id(notifId).userId(ownerId).isRead(false).build();

        when(repository.findById(notifId)).thenReturn(Optional.of(notif));

        assertThrows(IllegalArgumentException.class, () ->
                service.markAsRead(notifId, UUID.randomUUID())
        );
    }

    @Test
    void markAsRead_shouldMarkAsRead_whenCorrectUser() {
        UUID notifId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        Notification notif = Notification.builder()
                .id(notifId).userId(userId).isRead(false).build();

        NotificationDTO dto = NotificationDTO.builder()
                .id(notifId).isRead(true).build();

        when(repository.findById(notifId)).thenReturn(Optional.of(notif));
        when(repository.save(notif)).thenReturn(notif);
        when(mapper.toDTO(notif)).thenReturn(dto);

        NotificationDTO result = service.markAsRead(notifId, userId);

        assertTrue(result.getIsRead());
        verify(repository, times(1)).save(notif);
    }

    @Test
    void markAllAsRead_shouldMarkAllAsRead() {
        UUID userId = UUID.randomUUID();

        Notification n1 = Notification.builder().id(UUID.randomUUID()).userId(userId).isRead(false).build();
        Notification n2 = Notification.builder().id(UUID.randomUUID()).userId(userId).isRead(false).build();

        when(repository.findByUserIdOrderByCreatedAtDesc(userId)).thenReturn(List.of(n1, n2));

        service.markAllAsRead(userId);

        assertTrue(n1.getIsRead());
        assertTrue(n2.getIsRead());
        verify(repository, times(1)).saveAll(anyList());
    }
}