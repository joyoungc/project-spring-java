package io.joyoungc.batch;

import io.joyoungc.application.config.ApplicationConfig;
import io.joyoungc.infrastructure.configuration.PersistenceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ApplicationConfig.class, PersistenceConfig.class})
public class ServerBatchApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerBatchApplication.class, args);
    }
}
