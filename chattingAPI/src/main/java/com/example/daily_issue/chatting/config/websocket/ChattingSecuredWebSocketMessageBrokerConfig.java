package com.example.daily_issue.chatting.config.websocket;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-11-24
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-11-24)
 */

import com.example.daily_issue.chatting.config.websocket.interceptor.AddMessageSenderInterceptor;
import com.example.daily_issue.chatting.config.websocket.interceptor.RoomInfoValidationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

/**
 *
 *
 */
@Profile("message&secured")
@Configuration
public class ChattingSecuredWebSocketMessageBrokerConfig
        extends AbstractSecurityWebSocketMessageBrokerConfigurer
{

    @Autowired
    AddMessageSenderInterceptor addMessageSenderInterceptor;
    @Autowired
    RoomInfoValidationInterceptor roomInfoValidationInterceptor;

    //@formatter:off
    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {

        messages
                // except MESSAGE / SUBSCRIBE
                .nullDestMatcher().authenticated()
                //.simpSubscribeDestMatchers("/user/queue/errors").permitAll()
                .simpDestMatchers(MessageURIConsts.APPLICATION + "/**").authenticated()
                .simpDestMatchers(MessageURIConsts.APPLICATION + "/**").hasRole("USER")
                .simpSubscribeDestMatchers(MessageURIConsts.TOPIC + "/**").authenticated()
                .simpSubscribeDestMatchers(MessageURIConsts.TOPIC + "/**").hasRole("USER")
                // deny other subscribe uri
                .simpTypeMatchers(SimpMessageType.MESSAGE, SimpMessageType.SUBSCRIBE).denyAll()
                .anyMessage().denyAll()
        ;
    }
    //@formatter:on

    @Override
    protected boolean sameOriginDisabled() {
        //return true;
        return super.sameOriginDisabled();
    }


    @Override
    protected void customizeClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(
                roomInfoValidationInterceptor
                , addMessageSenderInterceptor);
    }

    /*@Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.interceptors()
    }*/
}
