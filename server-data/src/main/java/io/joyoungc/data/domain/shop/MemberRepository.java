package io.joyoungc.data.domain.shop;

import org.springframework.data.jpa.repository.JpaRepository;

/***
 * Created by Aiden Jeong on 2021.12.13
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
}
