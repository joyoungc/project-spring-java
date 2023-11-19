package io.joyoungc.api.common.configuration;

import io.joyoungc.domain.order.DiscountPolicy;
import io.joyoungc.domain.order.FixedDiscountPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Aiden Jeong on 2021.09.22
 */
@Configuration
public class ServerApiConfig {

    @Bean
    public DiscountPolicy discountPolicy() {
        return new FixedDiscountPolicy();
    }

}
