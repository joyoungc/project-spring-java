package io.joyoungc.infrastructure.persistence.mapper;

import io.joyoungc.domain.member.Member;
import io.joyoungc.domain.order.Order;
import io.joyoungc.infrastructure.persistence.entity.MemberEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = OrderMapper.class)
public abstract class MemberMapper {

    @Mapping(target = "orders", ignore = true)
    public abstract Member toMember(MemberEntity memberEntity);

    public abstract List<Member> toMemberList(List<MemberEntity> memberEntities);

    @AfterMapping
    protected void setOrders(@MappingTarget Member member, MemberEntity entity) {
        List<Order> orders = OrderMapper.INSTANCE.toOrderList(entity.getOrders());
        member.setOrders(orders);
    }
}
