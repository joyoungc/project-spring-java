package io.joyoungc.infrastructure.client.httpapi;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface HttpApiClient {

    @GetExchange("/api/{type}")
    String get(@PathVariable("type") String type);
}
