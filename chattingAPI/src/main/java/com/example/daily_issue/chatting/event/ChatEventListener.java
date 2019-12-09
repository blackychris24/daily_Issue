package com.example.daily_issue.chatting.event;

import com.example.daily_issue.chatting.config.websocket.MessageURIConsts;
import com.example.daily_issue.chatting.domain.message.ChatMessageType;
import com.example.daily_issue.chatting.domain.message.ChatMessageVO;
import com.example.daily_issue.chatting.security.domain.LoginUser;
import com.example.daily_issue.chatting.service.ChatLoginUserSecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.user.SimpSession;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.security.Principal;
import java.util.stream.Stream;

@Component
@Slf4j
public class ChatEventListener {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private SimpUserRegistry simpUserRegistry;

    @Autowired
    private ChatLoginUserSecurityService chatSecurityService;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser principal = chatSecurityService.getPrincipal(event.getUser());
        log.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        long count = getUserSessionCount(event.getUser());
        if(count <= 1)
        {
            LoginUser principal = chatSecurityService.getPrincipal(event.getUser());

            String username = principal.getUserNickName();
            log.info("User Disconnected : " + username);

            ChatMessageVO chatMessage = new ChatMessageVO();
            chatMessage.setType(ChatMessageType.LEAVE);
            chatMessage.setSender(username);
            chatMessage.setContent(username + " left!!!");

            messagingTemplate.convertAndSend(
                    MessageURIConsts.TOPIC+"/"+principal.getCurrentRoomId()+MessageURIConsts.CHAT
                    , chatMessage);
        }
    }

    @EventListener
    public void handleWebSocketSubscribeListener(SessionSubscribeEvent event) {
        // TODO: 2019-12-08 subscribe 할 때 사용자의 정보를 읽어, 기존 방이 존재하면, 해당 방으로 subscribe 할 수 있는지?? 이건 connection인지?
        long count = getUserSessionCount(event.getUser());
        // 접속한 사용자가 없는 경우에만
        if(count <= 0)
        {
            LoginUser principal = chatSecurityService.getPrincipal(event.getUser());

            String username = principal.getUserNickName();
            log.info("User Subscribe : " + username);

            ChatMessageVO chatMessage = new ChatMessageVO();
            chatMessage.setType(ChatMessageType.JOIN);
            chatMessage.setSender(username);
            chatMessage.setContent(username + " joined!!!");

            messagingTemplate.convertAndSend(
                    MessageURIConsts.TOPIC+"/"+principal.getCurrentRoomId()+MessageURIConsts.CHAT
                    , chatMessage);
        }
    }
    @EventListener
    public void handleWebSocketUnSubscribeListener(SessionUnsubscribeEvent event) {
        LoginUser principal = chatSecurityService.getPrincipal(event.getUser());
    }


    // 해당 id로 해당 방에 로그인 된 session의 갯수
    private long getUserSessionCount(Principal wsPrincipal)
    {

        LoginUser principal = chatSecurityService.getPrincipal(wsPrincipal);
        if(principal == null || principal.getCurrentRoomId() == null)
        {
            return 0;
        }
        String destination = MessageURIConsts.TOPIC+"/"+principal.getCurrentRoomId()+MessageURIConsts.CHAT;

        // 현재 접속자의
        SimpUser user = simpUserRegistry.getUser(principal.getUsername());
        // 모든 session들 중에서, destination이 동일한 것의 수가 양수인 경우 (즉, 현재 destination과 동일한 session만 가져 왔을 때)
        Stream<SimpSession> simpSessionStream = user.getSessions().stream().filter(s -> {
            long count = s.getSubscriptions().stream().filter(sub -> sub.getDestination().equals(destination)).count();
            return count > 0;
        });
        // session이 1보다 크다면 아직 나의 계정이 해당 방에 존재한다는 뜻이고,
        // session이 1이라면 마지막이란 뜻
        long count = simpSessionStream.count();

        return count;
    }
}
