package io.joyoungc.infrastructure.persistence.mapper;

import io.joyoungc.domain.member.Member;
import io.joyoungc.domain.order.Order;
import io.joyoungc.infrastructure.persistence.entity.MemberEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = OrderMapper.class)
public interface MemberMapper {
    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    @Mapping(target = "orders", ignore = true)
    Member toMember(MemberEntity memberEntity);

    List<Member> toMemberList(List<MemberEntity> memberEntities);


    @AfterMapping
    default void setOrders(@MappingTarget Member member, MemberEntity entity) {
        List<Order> orders = OrderMapper.INSTANCE.toOrderList(entity.getOrders());
        member.setOrders(orders);
    }
}
