package io.joyoungc.config.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/***
 * Created by Aiden Jeong on 2021.11.14
 */
@Configuration
@EnableCaching
public class ServerCacheConfig {

    public static final String CACHE_JDK_MANAGER = "redisCacheJdkManager";

    /*
     * redis cache (json)
     *
     * @param connectionFactory
     * @return
     */
//    @Primary
//    @Bean
//    public CacheManager cacheManager(LettuceConnectionFactory connectionFactory) {
//        RedisCacheConfiguration redisConfiguration = RedisCacheConfiguration.defaultCacheConfig()
//                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
//                .disableCachingNullValues()
//                .entryTtl(Duration.ofMinutes(1));
//        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory)
//                .cacheDefaults(redisConfiguration).build();
//    }

    /*
     * redis cache (jdk)
     *
     * @param connectionFactory
     * @return
     */
//    @Bean
//    public CacheManager redisCacheJdkManager(LettuceConnectionFactory connectionFactory) {
//        RedisCacheConfiguration redisConfiguration = RedisCacheConfiguration.defaultCacheConfig()
//                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
//                .disableCachingNullValues()
//                .entryTtl(Duration.ofMinutes(1));
//        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory)
//                .cacheDefaults(redisConfiguration).build();
//    }
}
