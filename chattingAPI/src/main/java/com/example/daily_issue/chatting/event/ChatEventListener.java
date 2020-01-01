package com.example.daily_issue.chatting.event;

import com.example.daily_issue.chatting.domain.message.ChatMessageType;
import com.example.daily_issue.chatting.domain.message.ChatMessageVO;
import com.example.daily_issue.chatting.security.domain.LoginUser;
import com.example.daily_issue.chatting.service.websocket.ChatLoginUserSecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

@Component
@Slf4j
public class ChatEventListener {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    @Autowired
    private ChatLoginUserSecurityService chatSecurityService;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        LoginUser principal = chatSecurityService.getPrincipal(event.getUser());
        log.info("Received a new web socket connection : "+principal.getUserNickName());
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        LoginUser principal = chatSecurityService.getPrincipal(headerAccessor);
        String userNickName = principal.getUserNickName();

        ChatMessageVO chatMessage = new ChatMessageVO();
        chatMessage.setType(ChatMessageType.LEAVE);
        chatMessage.setSender(userNickName);
        chatMessage.setContent(userNickName + " left!!!");

        messagingTemplate.convertAndSend(
                headerAccessor.getDestination()
                , chatMessage);
    }

    @EventListener
    public void handleWebSocketSubscribeListener(SessionSubscribeEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        LoginUser principal = chatSecurityService.getPrincipal(headerAccessor);
        String userNickName = principal.getUserNickName();

        ChatMessageVO chatMessage = new ChatMessageVO();
        chatMessage.setType(ChatMessageType.JOIN);
        chatMessage.setSender(userNickName);
        chatMessage.setContent(userNickName + " joined!!!");

        messagingTemplate.convertAndSend(
                //MessageURIConsts.TOPIC+"/"+principal.getCurrentRoomId()+MessageURIConsts.CHAT
                headerAccessor.getDestination()
                , chatMessage);
    }
    @EventListener
    public void handleWebSocketUnSubscribeListener(SessionUnsubscribeEvent event) {

    }
}
