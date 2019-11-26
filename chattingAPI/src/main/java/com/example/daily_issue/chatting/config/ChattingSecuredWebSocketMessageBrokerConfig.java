package com.example.daily_issue.chatting.config;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-11-24
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-11-24)
 */

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 *
 *
 */
@Profile("message&secured")
@Configuration
@EnableWebSocketMessageBroker
public class ChattingSecuredWebSocketMessageBrokerConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

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

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(MessageURIConsts.ENDPOINT).withSockJS();
    }

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {

        // authenticate
        messages.anyMessage().authenticated();
        messages.simpSubscribeDestMatchers(MessageURIConsts.APPLICATION + "/**").authenticated();
        messages.simpSubscribeDestMatchers(MessageURIConsts.TOPIC + "/**").authenticated();

        // authorize
        messages.anyMessage().hasRole("USER");
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true;
        //return super.sameOriginDisabled();
    }


    @Override
    protected void customizeClientInboundChannel(ChannelRegistration registration) {
        super.customizeClientInboundChannel(registration);
    }
}
