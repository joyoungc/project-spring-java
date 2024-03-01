package io.joyoungc.api.common.messages;

import io.joyoungc.api.common.configuration.messages.JsonMessageSource;
import io.joyoungc.api.common.configuration.messages.MessageLocale;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JsonMessageSourceTest {

    MessageSource messageSource = new JsonMessageSource("i18n");

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