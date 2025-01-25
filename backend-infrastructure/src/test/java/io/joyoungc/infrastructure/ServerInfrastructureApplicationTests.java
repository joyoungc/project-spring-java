package io.joyoungc.infrastructure;

import io.joyoungc.infrastructure.constant.Profiles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(initializers = ConfigDataApplicationContextInitializer.class)
@ActiveProfiles(Profiles.TEST)
class ServerInfrastructureApplicationTests {

    @Autowired
    Environment env;

    @Test
    void check_properties() {
        assertThat(env.getProperty("spring.datasource.username")).isEqualTo("app");
    }
}
