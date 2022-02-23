package io.joyoungc.data.shop.repository;

import io.joyoungc.data.shop.domain.Member;

import java.util.List;

/***
 * Created by Aiden Jeong on 2022.02.24
 */
public interface MemberQueryDslRepository {
    List<Member> findMembers();
}
