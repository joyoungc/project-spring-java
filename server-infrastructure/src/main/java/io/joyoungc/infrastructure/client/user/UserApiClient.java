package io.joyoungc.infrastructure.client.user;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface UserApiClient {

    @GetExchange("/api/v1/persons")
    ResponseUser getUser(@RequestParam String _locale, @RequestParam int _quantity ); // ?=ko_KR&_quantity=2
}
