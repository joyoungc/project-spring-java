package io.joyoungc.data.domain.shop;

import io.joyoungc.data.domain.AuditEntity;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/***
 * Created by Aiden Jeong on 2021.12.12
 */
@Entity
@Getter
public class Member extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

}
