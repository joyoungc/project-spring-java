/*
 * Created by Aiden Jeong on 2024.06.01
 */
package io.joyoungc.infrastructure.persistence.admin.entity;

import io.joyoungc.domain.model.admin.AdminUser;
import io.joyoungc.infrastructure.persistence.AuditEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name= "user")
@NoArgsConstructor
@Getter
@Setter
public class AdminUserEntity extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private String email;
    private AdminUser.Status status;

}
