package com.example.daily_issue.chatting.config.websocket;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-11-24
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-11-24)
 */

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.session.Session;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 *
 *
 */
@Profile("message")
@Configuration
@EnableWebSocketMessageBroker
public class ChattingWebSocketMessageBrokerConfig<S extends Session>
        extends AbstractSessionWebSocketMessageBrokerConfigurer<S>
          implements WebSocketMessageBrokerConfigurer
{

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        registry.setApplicationDestinationPrefixes(MessageURIConsts.APPLICATION);

        // in-memory
        registry.enableSimpleBroker(MessageURIConsts.TOPIC);
        /*
        registry.enableStompBrokerRelay("/topic")
                .setRelayHost("localhost")
                .setRelayPort(61613)
                .setClientLogin("guest")
                .setClientPasscode("guest");
        */
    }

    /*@Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(MessageURIConsts.ENDPOINT).withSockJS();
    }*/

    @Override
    protected void configureStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint(MessageURIConsts.ENDPOINT)
                .addInterceptors(new HttpSessionHandshakeInterceptor())
                .withSockJS();
    }

}
