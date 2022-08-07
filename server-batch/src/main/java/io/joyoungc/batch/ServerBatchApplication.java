package io.joyoungc.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class ServerBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerBatchApplication.class, args);
    }

}
