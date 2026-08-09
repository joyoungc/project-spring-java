package io.joyoungc.domain.model.member;


import io.joyoungc.domain.model.order.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Member {

    public Member(String name, Grade grade) {
        this.name = name;
        this.grade = grade;
    }

    private Long id;

    private String name;

    private String createdBy;

    private String modifiedBy;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private Grade grade;

    private Address address;

    private List<Order> orders = new ArrayList<>();
}
