package io.joyoungc.infrastructure.persistence.shop.repository;

import io.joyoungc.infrastructure.persistence.shop.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/***
 * Created by Aiden Jeong on 2021.12.13
 */
public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long>, MemberQueryDslRepository {
}
