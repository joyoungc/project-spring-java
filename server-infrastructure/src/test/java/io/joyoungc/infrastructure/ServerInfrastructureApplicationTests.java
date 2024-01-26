package io.joyoungc.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ServerInfrastructureApplicationTests {

    @Autowired
    Environment env;

    @Test
    void env_test() {
        assertThat(env.getProperty("spring.datasource.username")).isEqualTo("app");
    }

}
