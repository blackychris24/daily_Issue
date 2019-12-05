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
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

/**
 *
 *
 */
@Slf4j
@Component
public class TopicSubscriptionInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.SEND.equals(headerAccessor.getCommand())
                && headerAccessor.getSessionAttributes().get("roomId") != null
        ) {
            UsernamePasswordAuthenticationToken authentication = ((UsernamePasswordAuthenticationToken)headerAccessor.getUser());
            LoginUser principal = ((LoginUser)authentication.getPrincipal());
            if(!validateSubscription(principal, headerAccessor.getDestination()))
            {
                ChatMessageVO errPayload = new ChatMessageVO();
                errPayload.setType(ChatMessageType.ERROR);
                errPayload.setSender(principal.getUserNickName());
                errPayload.setContent("No permission for this topic!!!");

                Message<ChatMessageVO> errMsg = MessageBuilder.createMessage(errPayload, message.getHeaders());
                channel.send(errMsg);

                throw new IllegalArgumentException("No permission for this topic");
            }
        }
        return message;
    }


    private boolean validateSubscription(LoginUser principal, String topicDestination)
    {
        boolean result = topicDestination.startsWith(MessageURIConsts.TOPIC + "/" + principal.getCurrentRoomId());
        if(result)
        {
            log.debug("Validate subscription for {} to topic {}", principal.getUsername(), topicDestination);
        }
        //Additional validation logic coming here
        return result;
    }
}
