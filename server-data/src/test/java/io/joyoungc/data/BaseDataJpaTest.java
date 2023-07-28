package io.joyoungc.data;

import io.joyoungc.common.Profiles;
import io.joyoungc.data.configuration.DataSourceConfig;
import io.joyoungc.data.configuration.JpaDataConfig;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({DataSourceConfig.class, JpaDataConfig.class})
@ActiveProfiles(Profiles.TEST)
public abstract class BaseDataJpaTest {
}
