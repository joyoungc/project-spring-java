package io.joyoungc.api.common.configuration.messages;

import org.springframework.context.support.AbstractMessageSource;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JsonMessageSource extends AbstractMessageSource {

    private final Map<String, Map<Locale, String>> cachedMessagesMap = new ConcurrentHashMap<>();
    private final MessageResourceLoader messageResourceLoader = new MessageResourceLoader();

    public JsonMessageSource(String location) {
        initialize(location);
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        String message = cachedMessagesMap.get(code).get(locale);
        return new MessageFormat(message, locale);
    }

    @Override
    protected String resolveCodeWithoutArguments(String code, Locale locale) {
        return getStringOrNull(code, locale);
    }

    private void initialize(String location) {
        for (MessageLocale locale : MessageLocale.values()) {
            Map<String, String> map = messageResourceLoader.getMessageMap(location, locale.name());
            map.forEach((key, value) -> {
                if (cachedMessagesMap.get(key) == null) {
                    Map<Locale, String> target = new HashMap<>();
                    target.put(locale.getLocale(), value);
                    cachedMessagesMap.put(key, target);
                } else {
                    cachedMessagesMap.get(key).put(locale.getLocale(), value);
                }
            });
        }
    }

    private String getStringOrNull(String code, Locale locale) {
        try {
            return cachedMessagesMap.get(code).get(locale);
        } catch (NullPointerException e) {
            // Assume key not found for some other reason
            // -> do NOT throw the exception to allow for checking parent message source.
        }
        return null;
    }
}
