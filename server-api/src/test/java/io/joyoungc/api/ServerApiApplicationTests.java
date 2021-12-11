package io.joyoungc.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ServerApiApplicationTests {

    @Autowired
    Environment env;

    @Test
    void propertiesTest() {
        Assertions.assertThat(env.getProperty("server.port")).isEqualTo("9010");
        Assertions.assertThat(env.getProperty("spring.output.ansi.enabled")).isEqualTo("always");
        Assertions.assertThat(env.getProperty("logging.level.jdbc.sqlonly")).isEqualTo("info");
        Assertions.assertThat(env.getProperty("spring.redis.port")).isEqualTo("6379");
    }
}
