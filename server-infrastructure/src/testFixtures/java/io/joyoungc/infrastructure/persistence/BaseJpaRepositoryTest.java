package io.joyoungc.infrastructure.persistence;

import io.joyoungc.infrastructure.persistence.configuration.DataSourceConfig;
import io.joyoungc.infrastructure.persistence.configuration.JpaDataConfig;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({DataSourceConfig.class, JpaDataConfig.class})
public abstract class BaseJpaRepositoryTest {
}