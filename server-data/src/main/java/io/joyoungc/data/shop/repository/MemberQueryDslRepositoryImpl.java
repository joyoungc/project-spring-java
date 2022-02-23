package io.joyoungc.data.shop.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.joyoungc.data.shop.domain.Member;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static io.joyoungc.data.shop.domain.QMember.member;

/***
 * Created by Aiden Jeong on 2022.02.18
 */
@RequiredArgsConstructor
public class MemberQueryDslRepositoryImpl implements MemberQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public List<Member> findMembers() {
        return queryFactory
                .selectFrom(member)
                .fetch();
    }

}
