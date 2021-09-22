package io.joyoungc.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "io.joyoungc")
public class ServerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApiApplication.class, args);
    }

}
