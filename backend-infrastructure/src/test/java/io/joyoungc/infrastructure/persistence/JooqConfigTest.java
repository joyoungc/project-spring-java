package io.joyoungc.infrastructure.persistence;

import io.joyoungc.infrastructure.constant.Profiles;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.test.context.ActiveProfiles;

import static io.joyoungc.infrastructure.database.tables.Member.MEMBER;
import static org.assertj.core.api.Assertions.assertThat;


@JooqTest
@ActiveProfiles(Profiles.TEST)
class JooqConfigTest {

    @Autowired
    DSLContext dslContext;

    @Test
    void test_jooq() {
        Result<Record1<Long>> fetch = dslContext
                .select(MEMBER.ID).from(MEMBER)
                .fetch();
        assertThat(fetch).isNotNull();
    }
}
