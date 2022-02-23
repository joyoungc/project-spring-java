package io.joyoungc.data.shop.repository;

import io.joyoungc.data.shop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/***
 * Created by Aiden Jeong on 2021.12.13
 */
public interface MemberRepository extends JpaRepository<Member, Long>, MemberQueryDslRepository {
}
