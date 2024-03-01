package io.joyoungc.api;

import io.joyoungc.infrastructure.configuration.PersistenceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;

@SpringBootApplication
@Import({PersistenceConfig.class})
public class ServerApiApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ServerApiApplication.class, args);
        ConfigurableEnvironment environment = context.getEnvironment();
        System.out.println("==== Properties in the Environment ====");
        for (PropertySource<?> propertySource : environment.getPropertySources()) {
            System.out.println("propertySource = " + propertySource);
        }
    }

}
