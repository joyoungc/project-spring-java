package io.joyoungc.domain.member;


import io.joyoungc.domain.order.Order;
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

    private Long id;

    private String name;

    private String createdBy;

    private String modifiedBy;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private Grade grade;

    private Address address;

    private List<Order> orders = new ArrayList<>();
}
