package io.joyoungc.api.configuration.messages;

import lombok.Getter;

import java.util.Locale;

public enum MessageLocale {
    en_US(Locale.US),
    ko_KR(Locale.KOREA),
    ja_JP(Locale.JAPAN);

    @Getter
    private final Locale locale;

    MessageLocale(Locale locale) {
        this.locale = locale;
    }
}

