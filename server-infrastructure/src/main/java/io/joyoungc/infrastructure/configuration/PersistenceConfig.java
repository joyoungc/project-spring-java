package io.joyoungc.infrastructure.configuration;

import io.joyoungc.infrastructure.constant.Profiles;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ComponentScan(basePackages = {
        "io.joyoungc.infrastructure.persistence"
})
@Profile("!" + Profiles.WEBMVC)
public class PersistenceConfig {
}
