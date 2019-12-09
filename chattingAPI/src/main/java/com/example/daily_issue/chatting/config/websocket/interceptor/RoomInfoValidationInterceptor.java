package com.example.daily_issue.chatting.config.websocket.interceptor;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-12-05
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-12-05)
 */

import com.example.daily_issue.chatting.config.websocket.MessageURIConsts;
import com.example.daily_issue.chatting.domain.message.ChatMessageType;
import com.example.daily_issue.chatting.domain.message.ChatMessageVO;
import com.example.daily_issue.chatting.security.domain.LoginUser;
import com.example.daily_issue.chatting.service.ChatLoginUserSecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.converter.CompositeMessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 *
 *
 */
@Slf4j
@Component
public class RoomInfoValidationInterceptor implements ChannelInterceptor {

    @Autowired
    ChatLoginUserSecurityService chatSecurityService;
    @Autowired
    CompositeMessageConverter compositeMessageConverter;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        LoginUser principal = chatSecurityService.getPrincipal(headerAccessor);

        if(StompCommand.SEND.equals(headerAccessor.getCommand()))
        {
            if (chatSecurityService.hasParticipatingRoom(headerAccessor))
            {
                // will delete (for test)
                ChatMessageVO payload = (ChatMessageVO) compositeMessageConverter.fromMessage(message, ChatMessageVO.class);

                if(!validateRoomId(principal, headerAccessor.getDestination()) ||
                        (payload.getContent() != null && payload.getContent().equals("ExecuteError"))) {
                    ChatMessageVO errPayload = new ChatMessageVO();
                    errPayload.setType(ChatMessageType.ERROR);
                    errPayload.setSender(principal.getUserNickName());
                    errPayload.setContent("No permission for this topic!!!");

                    Message<ChatMessageVO> errMsg = MessageBuilder.createMessage(errPayload, message.getHeaders());
                    channel.send(errMsg);

                    throw new IllegalArgumentException("No permission for this topic");
                }
            }
        }
        return message;
    }


    /*
    subscribe 시 등록된 방 번호로 message send 요청을 보내었는지 검증함.
    message send 시 임의로 다른 방으로 message 전달 시 오류를 출력한다.
     */
    private boolean validateRoomId(LoginUser principal, String topicDestination)
    {
        boolean result = topicDestination.startsWith(MessageURIConsts.TOPIC + "/" + principal.getCurrentRoomId()+"/");
        if(result)
        {
            log.debug("Validate subscription for {} to topic {}", principal.getUsername(), topicDestination);
        }
        //Additional validation logic coming here
        return result;
    }
}
