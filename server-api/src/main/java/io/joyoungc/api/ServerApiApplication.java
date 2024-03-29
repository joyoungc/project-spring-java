package io.joyoungc.api;

import io.joyoungc.infrastructure.configuration.PersistenceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({PersistenceConfig.class})
public class ServerApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerApiApplication.class, args);
    }

}
