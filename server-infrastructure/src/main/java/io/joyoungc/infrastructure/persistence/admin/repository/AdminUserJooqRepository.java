package io.joyoungc.infrastructure.persistence.admin.repository;


import io.joyoungc.domain.admin.AdminUser;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static io.joyoungc.infrastructure.database.Tables.ADMIN_USER;

@Repository
@RequiredArgsConstructor
public class AdminUserJooqRepository {

    private final DSLContext context;

    public List<AdminUser> selectUsers() {
        return context.select(ADMIN_USER.ID)
                .from(ADMIN_USER)
                .fetchInto(AdminUser.class);
    }
}
