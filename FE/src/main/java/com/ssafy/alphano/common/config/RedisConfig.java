package com.ssafy.alphano.common.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
public class RedisConfig {
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        try {
            config.setHostName("3.35.51.16");
            config.setPort(6379);
        } catch (RedisConnectionFailureException e){
            e.printStackTrace();
        }
        return new LettuceConnectionFactory(config);
    }

    @Bean
    @Qualifier("redisTemplate")
    public RedisTemplate<String, Object> redisTemplate () {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        return template;
    }

//    @Bean
//    @Qualifier("currentPriceRedisTemplate")
//    public RedisTemplate<String, String> currentPriceRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<String, String> template = new RedisTemplate<>();
//        template.setConnectionFactory(redisConnectionFactory);
//        template.setKeySerializer(new StringRedisSerializer());
//
//        // Value의 Serializer를 설정합니다.
//        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(List.class));
//
//        return template;
//    }
}
