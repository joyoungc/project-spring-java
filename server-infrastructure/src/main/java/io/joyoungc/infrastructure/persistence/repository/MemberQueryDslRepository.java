package io.joyoungc.infrastructure.persistence.repository;

import io.joyoungc.data.jpa.domain.MemberEntity;
import io.joyoungc.domain.member.Grade;

import java.util.List;

/***
 * Created by Aiden Jeong on 2022.02.24
 */
public interface MemberQueryDslRepository {
    List<MemberEntity> findMembers(Grade grade);
}
