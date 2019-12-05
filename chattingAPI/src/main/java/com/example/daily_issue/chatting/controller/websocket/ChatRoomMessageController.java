package com.example.daily_issue.chatting.controller.websocket;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-11-25
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-11-25)
 */

import com.example.daily_issue.chatting.config.websocket.MessageURIConsts;
import com.example.daily_issue.chatting.domain.message.ChatMessageVO;
import com.example.daily_issue.chatting.security.service.SecurityService;
import com.example.daily_issue.chatting.service.ChatSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 *
 *
 */
@RestController
@MessageMapping(MessageURIConsts.TOPIC_CHAT_CONTEXT)
public class ChatRoomMessageController {

    @Autowired
    SecurityService securityService;
    @Autowired
    ChatSecurityService chatSecurityService;

    @MessageMapping("/{roomId}/chat.sendMessage")
    @SendTo(MessageURIConsts.TOPIC + "/{roomId}/chat")
    public ChatMessageVO sendMessage(@DestinationVariable Long roomId, @Payload ChatMessageVO chatMessage) throws IOException {
        return chatMessage;
    }

    @MessageMapping("/{roomId}/chat.addUser")
    @SendTo(MessageURIConsts.TOPIC + "/{roomId}/chat")
    public ChatMessageVO addUser(@DestinationVariable Long roomId,
                                 @Payload ChatMessageVO chatMessage,
                                 StompHeaderAccessor headerAccessor) {



        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    @SubscribeMapping("/{roomId}/chat")
    public void subscribe(@DestinationVariable Long roomId, StompHeaderAccessor headerAccessor) {
        if(chatSecurityService.isFisrtVisit())
        {
            chatSecurityService.updateRoomId(roomId);
            headerAccessor.setUser(securityService.getAuthentication());
            headerAccessor.getSessionAttributes().put("roomId", roomId);
        }
        // login한 member의 정보를 가져온다.
        // 만일, 해당 방의 member가 아니라면 member로 등록함.

    }
}
