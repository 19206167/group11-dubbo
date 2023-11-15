package com.example.group11.service.notification.util;

import com.example.group11.service.notification.NotificationService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * FileName: NotificationServiceImpl.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description ToDo
 * @Date 2023/10/14 19:28
 */
@DubboService(version="1.0.0", interfaceClass = com.example.group11.service.notification.NotificationService.class)
public class NotificationServiceImpl implements NotificationService {
    /**
     This module has been replaced in the webSocketServer of common API package.
     so that every service that needs to use the notification function can use @Autowired to inject WebSocketServer by introducing the common API dependency.
     So far, only QAService(sendPrompt function) has used this notification function
     */
}
