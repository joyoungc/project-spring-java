package io.joyoungc.api.common.configuration.format;

import io.joyoungc.api.common.configuration.format.annotation.TimestampToDate;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

public class TimestampFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<TimestampToDate> {
    @Override
    public Set<Class<?>> getFieldTypes() {
        return new HashSet<>(asList(new Class<?>[]{Date.class, LocalDateTime.class}));
    }

    @Override
    public Printer<?> getPrinter(TimestampToDate annotation, Class<?> fieldType) {
        return getFormatter(annotation, fieldType);
    }

    @Override
    public Parser<?> getParser(TimestampToDate annotation, Class<?> fieldType) {
        return getFormatter(annotation, fieldType);
    }

    private Formatter<?> getFormatter(TimestampToDate annotation, Class<?> fieldType) {
        if (fieldType.isAssignableFrom(Date.class)) {
            return new DateFormatter();
        } else {
            return new LocalDateTimeFormatter();
        }
    }
}
