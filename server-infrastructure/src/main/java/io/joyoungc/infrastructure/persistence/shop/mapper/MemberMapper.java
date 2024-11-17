package io.joyoungc.infrastructure.persistence.shop.mapper;

import io.joyoungc.domain.model.member.Member;
import io.joyoungc.domain.model.order.Order;
import io.joyoungc.infrastructure.persistence.shop.entity.MemberEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = OrderMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
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
