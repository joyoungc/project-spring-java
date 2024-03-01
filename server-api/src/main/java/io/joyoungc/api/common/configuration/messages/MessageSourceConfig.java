package io.joyoungc.api.common.configuration.messages;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageSourceConfig {

    @Bean
    public MessageSource messageSource() {
        return new JsonMessageSource("i18n");
    }

}
