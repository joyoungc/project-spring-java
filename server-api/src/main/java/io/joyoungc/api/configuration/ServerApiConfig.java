package io.joyoungc.api.configuration;

import io.joyoungc.api.ApiCodes;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.util.HashMap;
import java.util.Map;

/***
 * Created by Aiden Jeong on 2021.09.22
 */
@Configuration
public class ServerApiConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);
        return modelMapper;
    }


    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return builder -> {
            Map<String, RedisCacheConfiguration> caches = new HashMap<>();
            for (ApiCodes.Cache cache : ApiCodes.Cache.values()) {
                caches.put(cache.getName(), RedisCacheConfiguration.defaultCacheConfig()
                        .disableCachingNullValues()
                        .prefixCacheNameWith(activeProfile + "::")
                        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                        .entryTtl(cache.getTtl())
                );
            }
            builder.withInitialCacheConfigurations(caches);
        };
    }
}
