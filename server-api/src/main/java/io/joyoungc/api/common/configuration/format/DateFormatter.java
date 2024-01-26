package io.joyoungc.api.common.configuration.format;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

public class DateFormatter implements Formatter<Date> {

    @Override
    public Date parse(String text, Locale locale) throws ParseException {
        return new Date(Long.parseLong(text));
    }

    @Override
    public String print(Date object, Locale locale) {
        return object.toString();
    }
}
