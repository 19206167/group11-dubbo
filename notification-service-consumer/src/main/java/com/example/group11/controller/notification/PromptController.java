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
     * 通过WebSocket实现后台向前端的消息推送
     *  project1部署在单台服务器上，因此直接由各个Controller在各自接口中通过websocket向browser推送.
     *  project2为微服务架构，因此该Controller作为一个单体应用部署在一个专门的消息推送服务器上，其他后台服务器通过kafka向该消息推送服务器发送消息，
     *  由该消息推送服务器通过webSocket向browser统一推送全系统的消息
     */

    @DubboReference(version="1.0.0", interfaceClass = com.example.group11.service.notification.NotificationService.class)
    NotificationService notificationService;

//    @GetMapping("")
//    @ApiOperation(notes = "专门", value = "根据多条件查询全部回答的分页列表", tags = "问题管理")

}
