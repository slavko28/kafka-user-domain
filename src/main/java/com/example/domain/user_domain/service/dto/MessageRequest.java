package com.example.domain.user_domain.service.dto;

import com.example.domain.user_domain.service.emuns.NotificationAction;
import com.example.domain.user_domain.service.emuns.NotificationType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageRequest {

    private Long deviceId;
    private Long externalId;
    private String userLogin;
    private String topicName;
    private String messageBody;
    private NotificationType notificationType;
    private NotificationAction notificationAction;
}
