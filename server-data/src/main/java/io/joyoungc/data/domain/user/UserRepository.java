package io.joyoungc.data.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

/***
 * Created by Aiden Jeong on 2021.09.22
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
