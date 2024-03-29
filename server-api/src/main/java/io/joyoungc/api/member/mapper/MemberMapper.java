package io.joyoungc.api.member.mapper;

import io.joyoungc.api.member.request.CreateMemberRequest;
import io.joyoungc.api.member.response.MemberResponse;
import io.joyoungc.api.order.mapper.OrderMapper;
import io.joyoungc.domain.member.Member;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = OrderMapper.class)
public interface MemberMapper {
    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);
    // RequestDto -> MessageBodyDto 매핑
    Member toMember(CreateMemberRequest requestDto);

    MemberResponse toMemberResponse(Member member);
    List<MemberResponse> toMemberResponseList(List<Member> members);
}
