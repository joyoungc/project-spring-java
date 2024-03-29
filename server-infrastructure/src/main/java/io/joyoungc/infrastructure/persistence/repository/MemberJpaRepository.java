package io.joyoungc.infrastructure.persistence.repository;

import io.joyoungc.infrastructure.persistence.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/***
 * Created by Aiden Jeong on 2021.12.13
 */
public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long>, MemberQueryDslRepository {
}
