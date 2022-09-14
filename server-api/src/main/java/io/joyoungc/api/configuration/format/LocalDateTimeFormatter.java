package io.joyoungc.api.configuration.format;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {

    @Override
    public LocalDateTime parse(String text, Locale locale) throws ParseException {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(text)), TimeZone.getDefault().toZoneId());
    }

    @Override
    public String print(LocalDateTime object, Locale locale) {
        return object.toString();
    }
}
