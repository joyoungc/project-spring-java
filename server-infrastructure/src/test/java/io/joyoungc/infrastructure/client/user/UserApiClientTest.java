package io.joyoungc.infrastructure.client.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.joyoungc.infrastructure.constant.Profiles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(initializers = ConfigDataApplicationContextInitializer.class, classes = {UserApiConfiguration.class})
@ActiveProfiles(Profiles.TEST)
class UserApiClientTest {

    @Autowired
    UserApiClient userApiClient;

    @Test
    void getUser() throws JsonProcessingException {
        // given

        // when
        ResponseUser user = userApiClient.getUser("ko_KR", 2);

        // then
        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(user));
    }
}