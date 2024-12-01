package io.joyoungc.application.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"io.joyoungc.application.input"}, lazyInit = true)
public class ApplicationConfig {
}
