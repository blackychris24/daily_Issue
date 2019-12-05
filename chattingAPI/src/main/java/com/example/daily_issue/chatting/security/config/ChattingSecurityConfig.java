package com.example.daily_issue.chatting.security.config;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-11-25
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-11-25)
 */

import com.example.daily_issue.chatting.config.websocket.MessageURIConsts;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 *
 */
@Configuration
@Order(Ordered.LOWEST_PRECEDENCE)
public class ChattingSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .requestMatchers(
                        PathRequest.toStaticResources().atCommonLocations()
                )
        ;
    }

    //@formatter:off
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.formLogin();
        http.httpBasic();

        http
                .csrf(csrf -> csrf
                        .ignoringAntMatchers(
                                MessageURIConsts.ENDPOINT,
                                MessageURIConsts.ENDPOINT + "/**"
                        )
//                        .csrfTokenRepository(new HttpSessionCsrfTokenRepository())
                )
                .headers(headers -> headers
                        .cacheControl()
                        .and()
                        .frameOptions().sameOrigin()
                )
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .antMatchers(MessageURIConsts.ENDPOINT).permitAll()
                        .anyRequest().authenticated()
                );
    }
    //@formatter:on
}
