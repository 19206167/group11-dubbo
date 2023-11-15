package com.example.group11.controller.notification;

import com.example.group11.service.notification.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/prompt")
public class PromptController {

    /**
     This module has been replaced in the webSocketServer of common API package.
     so that every service that needs to use the notification function can use @Autowired to inject WebSocketServer by introducing the common API dependency.
     So far, only QAService(sendPrompt function) has used this notification function
     */

    @DubboReference(version="1.0.0", interfaceClass = com.example.group11.service.notification.NotificationService.class)
    NotificationService notificationService;

//    @GetMapping("")
//    @ApiOperation(notes = "专门", value = "根据多条件查询全部回答的分页列表", tags = "问题管理")

}
