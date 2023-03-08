package io.joyoungc.api.configuration.messages;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JsonMessageSource extends AbstractMessageSource {

    private final Map<String, Map<Locale, String>> messageMap = new ConcurrentHashMap<>();

    public JsonMessageSource() {
        initialize();
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        String message = messageMap.get(code).get(locale);
        return new MessageFormat(message, locale);
    }

    private void initialize() {
        ObjectMapper mapper = new ObjectMapper();
        Resource resource = new ClassPathResource("messages.json");
        Map<String, Map<String, String>> map;
        try {
            map = mapper.readValue(resource.getInputStream(), Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        map.forEach((key, value) -> value.forEach((loc, msg) -> {
            Locale locale = Locale.KOREA;
            if ("ko".equals(loc)) {
                locale = Locale.KOREA;
            } else if ("ja".equals(loc)) {
                locale = Locale.JAPAN;
            } else if ("en".equals(loc)) {
                locale = Locale.US;
            }

            if (messageMap.get(key) == null) {
                Map<Locale, String> target = new HashMap<>();
                target.put(locale, msg);
                messageMap.put(key, target);
            } else {
                messageMap.get(key).put(locale, msg);
            }
        }));
    }
}
