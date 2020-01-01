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


    @MessageMapping("/{roomId}"+MessageURIConsts.CHAT+".sendMessage")
    @SendTo(MessageURIConsts.TOPIC + "/{roomId}" + MessageURIConsts.CHAT)
    public ChatMessageVO sendMessage(@DestinationVariable Long roomId
            , @Payload ChatMessageVO chatMessage
            , StompHeaderAccessor headerAccessor) throws IOException {
        return chatMessage;
    }

    @MessageMapping("/{roomId}"+MessageURIConsts.CHAT+".addUser")
    @SendTo(MessageURIConsts.TOPIC + "/{roomId}" + MessageURIConsts.CHAT)
    public ChatMessageVO addUser(@DestinationVariable Long roomId,
                                 @Payload ChatMessageVO chatMessage,
                                 StompHeaderAccessor headerAccessor) {
        return chatMessage;
    }

    @SubscribeMapping("/{roomId}" + MessageURIConsts.CHAT)
    public void subscribe(@DestinationVariable Long roomId, StompHeaderAccessor headerAccessor) {

    }
}
