package io.joyoungc.api;

import io.joyoungc.api.common.configuration.messages.MessageSourceConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(initializers = ConfigDataApplicationContextInitializer.class, classes = {MessageSourceConfig.class})
class ServerApiApplicationTests {

    @Autowired
    Environment env;

    @Autowired
    MessageSource messageSource;

    @Test
    void test_properties() {
        Assertions.assertThat(env.getProperty("server.port")).isEqualTo("9010");
        Assertions.assertThat(env.getProperty("logging.level.root")).isEqualTo("info");
    }

    @Test
    void test_message() {
        String message = messageSource.getMessage("hello.world", null, null, Locale.KOREA);
        String message1 = messageSource.getMessage("hello.world", null, null, Locale.JAPAN);
        String message2 = messageSource.getMessage("hello.world", null, null, Locale.US);
        assertThat(message).isNotEmpty();
        assertThat(message1).isNotEmpty();
        assertThat(message2).isNotEmpty();

        String message3 = messageSource.getMessage("number.1", null, null, Locale.KOREA);
        String message4 = messageSource.getMessage("number.1", null, null, Locale.JAPAN);
        String message5 = messageSource.getMessage("number.1", null, null, Locale.US);

        assertThat(message3).isNotEmpty();
        assertThat(message4).isNotEmpty();
        assertThat(message5).isNotEmpty();
        System.out.println(message + "\n" + message1 + "\n" + message2 + "\n" + message3 + "\n" + message4 + "\n" + message5);
    }
}
