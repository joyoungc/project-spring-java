package io.joyoungc.data.domain.shop;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/***
 * Created by Aiden Jeong on 2022.02.18
 */
@Repository
@RequiredArgsConstructor
public class MemberQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public List<Member> findMembers() {
        return queryFactory
                .selectFrom(QMember.member)
                .fetch();
    }

}
