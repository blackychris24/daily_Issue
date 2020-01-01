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
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 *
 *
 */
@Profile("embeddedRedis")
@Configuration
public class EmbeddedRedisServerConfig {

    @Autowired
    private RedisProperties redisProperties;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    private RedisServer redisServer;

    @PostConstruct
    public void startRedisServer()
    {
        redisServer = new RedisServer(redisProperties.getPort());
        redisServer.start();
    }

    @PreDestroy
    public void stopRedisServer()
    {
        if(redisConnectionFactory instanceof LettuceConnectionFactory)
        {
            ((LettuceConnectionFactory)redisConnectionFactory).destroy();
        }

        if(redisServer != null)
            redisServer.stop();

    }
}
