package io.joyoungc.infrastructure.client.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "client.user-api")
public class UserApiProperties {
    private String endpoint;
    private int connectionTimeout;
    private int readTimeout;
}
