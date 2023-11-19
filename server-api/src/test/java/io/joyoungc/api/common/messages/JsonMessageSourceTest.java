package io.joyoungc.api.common.messages;

import io.joyoungc.api.common.configuration.ServerApiWebConfig;
import io.joyoungc.api.common.configuration.messages.MessageLocale;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ServerApiWebConfig.class)
class JsonMessageSourceTest {

    @Autowired
    MessageSource messageSource;

    @Test
    void get_message_by_locales() {
        // Test for Locales
        for (MessageLocale locale : MessageLocale.values()) {
            String msg = messageSource.getMessage("hello.world", null, locale.getLocale());
            System.out.println("msg = " + msg);
            assertThat(msg).isNotBlank();
        }
    }

    @Test
    void get_message_throw_exception() {
        assertThatThrownBy(() -> messageSource.getMessage(null, null, Locale.JAPAN))
                .isInstanceOf(NoSuchMessageException.class);

        assertThatThrownBy(() -> messageSource.getMessage("agp.throw.exception", null, Locale.JAPAN))
                .isInstanceOf(NoSuchMessageException.class);
    }

}