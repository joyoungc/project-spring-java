package io.joyoungc.infrastructure.persistence.admin.repository;

import io.joyoungc.domain.admin.AdminUser;
import io.joyoungc.infrastructure.constant.Profiles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JooqTest
@ActiveProfiles(Profiles.TEST)
@Import(AdminUserJooqRepository.class)
class AdminUserJooqRepositoryTest {

    @Autowired
    AdminUserJooqRepository adminUserJooqRepository;

    @Test
    void test_selectAdminUsers() {
        List<AdminUser> adminUsers = adminUserJooqRepository.selectUsers();
        assertThat(adminUsers).isNotEmpty();
    }
}