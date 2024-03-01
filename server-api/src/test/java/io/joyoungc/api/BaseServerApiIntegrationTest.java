package io.joyoungc.api;

import io.joyoungc.infrastructure.constant.Profiles;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.WebApplicationContext;


/**
 * MockBean, SpyBean 이 사용되지 않는 Test 클래스에 대한 Integration 전용 abstract class
 *
 * <ul>
 * <li>Test 클래스에서 MockBean이나 SpyBean 같은 Bean에 대한 수정이 생기면 Spring Context 가 reload 되어 테스트 시간 증가</li>
 * <li>테스트에 필요한 Spring Context 를 재사용하여 속도 향상</li>
 * <li>각 method 에 transactional 적용</li>
 * </ul>
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class,
        MongoRepositoriesAutoConfiguration.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles(Profiles.TEST)
public abstract class BaseServerApiIntegrationTest {

    @Autowired
    protected WebApplicationContext context;
}
