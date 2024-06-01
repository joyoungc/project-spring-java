package io.joyoungc.infrastructure.persistence.shop.repository;

import io.joyoungc.domain.shop.member.Grade;
import io.joyoungc.infrastructure.persistence.shop.entity.MemberEntity;

import java.util.List;

/***
 * Created by Aiden Jeong on 2022.02.24
 */
public interface MemberQueryDslRepository {
    List<MemberEntity> findMembers(Grade grade);
}
