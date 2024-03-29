package io.joyoungc.api.common.configuration;


import io.joyoungc.api.common.configuration.format.TimestampFormatAnnotationFormatterFactory;
import io.joyoungc.api.common.configuration.messages.JsonMessageSource;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ServerApiWebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        TimestampFormatAnnotationFormatterFactory factory = new TimestampFormatAnnotationFormatterFactory();
        registry.addFormatterForFieldAnnotation(factory);
    }
}
