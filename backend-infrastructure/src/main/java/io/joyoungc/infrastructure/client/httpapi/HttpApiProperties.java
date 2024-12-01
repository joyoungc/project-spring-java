package io.joyoungc.infrastructure.client.httpapi;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "client.http-api")
public class HttpApiProperties {
    private String endpoint;
    private int connectionTimeout;
    private int readTimeout;
}
