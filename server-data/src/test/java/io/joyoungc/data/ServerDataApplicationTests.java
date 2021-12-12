package io.joyoungc.data;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ServerDataApplicationTests {

    @Autowired
    Environment env;

    @Test
    void envTest() {
        assertThat(env.getProperty("spring.data.mongodb.auto-index-creation")).isEqualTo("true");
    }

}
