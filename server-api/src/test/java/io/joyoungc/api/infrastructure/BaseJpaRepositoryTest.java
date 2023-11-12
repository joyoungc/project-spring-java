package io.joyoungc.api.infrastructure;

import io.joyoungc.data.configuration.DataSourceConfig;
import io.joyoungc.data.configuration.JpaDataConfig;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EntityScan(basePackages = "io.joyoungc.data.jpa.domain")
@Import({DataSourceConfig.class, JpaDataConfig.class})
public abstract class BaseJpaRepositoryTest {
}
