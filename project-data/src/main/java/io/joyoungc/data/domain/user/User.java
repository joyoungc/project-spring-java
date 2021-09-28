package io.joyoungc.data.domain.user;

import io.joyoungc.data.domain.AuditEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/***
 * Created by Aiden Jeong on 2021.09.22
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends AuditEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
