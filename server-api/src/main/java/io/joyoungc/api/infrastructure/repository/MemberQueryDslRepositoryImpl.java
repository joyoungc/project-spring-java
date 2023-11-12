package io.joyoungc.api.infrastructure.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.joyoungc.data.jpa.domain.MemberEntity;
import io.joyoungc.domain.member.Grade;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static io.joyoungc.data.jpa.domain.QMemberEntity.memberEntity;


/***
 * Created by Aiden Jeong on 2022.02.18
 */
@RequiredArgsConstructor
public class MemberQueryDslRepositoryImpl implements MemberQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public List<MemberEntity> findMembers(Grade grade) {
        return queryFactory
                .selectFrom(memberEntity)
                .where(
                        isGrade(grade)
                )
                .fetch();
    }

    private BooleanExpression isGrade(Grade grade) {
        return grade != null ? memberEntity.grade.eq(grade) : null;
    }

}
