package com.example.daily_issue.chatting.config.websocket.interceptor;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-12-05
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-12-05)
 */

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
public class AddMessageSenderInterceptor implements ChannelInterceptor {

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
            ChatMessageVO payload = (ChatMessageVO) compositeMessageConverter.fromMessage(message, ChatMessageVO.class);

            // 모든 message에 대해서 대화 nick name은 login된 사용자의 nickname으로 대처됨
            payload.setSender(principal.getUserNickName());
            //Message<ChatMessageVO> respMsg = MessageBuilder.fromMessage(message)
            message = MessageBuilder.fromMessage(message)
                    .withPayload(payload)
                    .setHeaders(headerAccessor)
                    .build();
        }

        return message;
    }
}
