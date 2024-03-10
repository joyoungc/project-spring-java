package io.joyoungc.infrastructure.cache;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.joyoungc.infrastructure.constant.Profiles;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.Map;

/***
 * Created by Aiden Jeong on 2021.11.14
 */
@Configuration
@EnableCaching
@Profile(Profiles.CACHE)
@EnableConfigurationProperties(ServerCacheProperties.class)
@RequiredArgsConstructor
public class ServerCacheConfig {

    private final ServerCacheProperties serverCacheProperties;

    @Value("${spring.data.redis.host}")
    private String redisHost;

//    @Value("${spring.data.redis.username}")
//    private String redisUsername;

//    @Value("${spring.data.redis.password}")
//    private String redisPassword;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    /**
     * Spring Boot 가 기본적으로 RedisCacheManager 를 자동 설정
     * Bean 을 선언하여 직접 설정한 RedisCacheConfiguration 이 적용
     */
    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration
                .defaultCacheConfig()
                .disableCachingNullValues()
                .entryTtl(Duration.ofMinutes(serverCacheProperties.getDefaultExpireMinute()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper())));
    }

    /**
     * cacheName 별로 캐시 설정을 위해 RedisCacheManagerBuilderCustomizer 를 사용
     */
    @Bean
    @ConditionalOnProperty(prefix = "cache", name = "cache-map-enabled", havingValue = "true")
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        Map<String, Integer> cacheNameMap = serverCacheProperties.getCacheMap();
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer =
                new GenericJackson2JsonRedisSerializer(objectMapper());
        return (builder) -> cacheNameMap.forEach(
                (string, integer) -> builder.withCacheConfiguration(string,
                        RedisCacheConfiguration
                                .defaultCacheConfig()
                                .disableCachingNullValues()
                                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(genericJackson2JsonRedisSerializer))
                                .entryTtl(Duration.ofMinutes(integer)))
        );
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration standaloneConfig = new RedisStandaloneConfiguration(redisHost, redisPort);
//        standaloneConfig.setUsername(redisUsername);
//        standaloneConfig.setPassword(redisPassword);

        LettuceClientConfiguration clientConfig =
                LettuceClientConfiguration.builder().build();
        return new LettuceConnectionFactory(standaloneConfig, clientConfig);
    }

    /**
     *
     * @return
     */
    private ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.EVERYTHING, JsonTypeInfo.As.PROPERTY);
        return objectMapper;
    }

}
