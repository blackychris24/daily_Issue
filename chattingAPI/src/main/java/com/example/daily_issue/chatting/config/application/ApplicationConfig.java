package com.example.daily_issue.chatting.config.application;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public ModelMapper getModelMapper()
    {
        return new ModelMapper();
    }


    // not global
    /*@Bean
    public ObjectMapper getObjectMapper()
    {
        ObjectMapper objectMapper = new ObjectMapper();;
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        return objectMapper;
    }*/
}
