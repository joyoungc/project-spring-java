package io.joyoungc.infrastructure.cache;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Getter @Setter
@ConfigurationProperties(prefix = "cache")
public class ServerCacheProperties {
    private int defaultExpireMinute = 10;
    private boolean cacheMapEnabled;
    private Map<String, Integer> cacheMap;
}