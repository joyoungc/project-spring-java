package io.joyoungc.data.jpa.domain;

import io.joyoungc.domain.member.Address;
import io.joyoungc.domain.member.Grade;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/***
 * Created by Aiden Jeong on 2021.12.12
 */
@Entity
@Table(name = "member")
@NoArgsConstructor
@Getter
@Setter
public class MemberEntity extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<OrderEntity> orders = new ArrayList<>();

    public MemberEntity(String name) {
        this.name = name;
    }

    public MemberEntity(String name, Grade grade) {
        this.name = name;
        this.grade = grade;
    }
}
