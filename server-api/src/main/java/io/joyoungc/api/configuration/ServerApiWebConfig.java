package io.joyoungc.api.configuration;

import io.joyoungc.api.configuration.format.TimestampFormatAnnotationFormatterFactory;
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
