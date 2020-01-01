package com.example.daily_issue.chatting.config.redis;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-11-28
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-11-28)
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

/**
 *
 *
 */
@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Autowired
    RedisProperties redisProperties;

    @Bean
    public RedisConnectionFactory redisConnectionFactory()
    {
        LettuceConnectionFactory factory = new LettuceConnectionFactory(
                redisProperties.getHost()
                ,redisProperties.getPort()
        );

        return factory;
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate()
    {
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }

}
