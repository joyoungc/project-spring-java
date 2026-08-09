package io.joyoungc.application.input;

import io.joyoungc.domain.model.member.Member;

import java.util.List;

public interface MemberUseCase {

    Long createMember(Member member);

    Member getMember(long userId);

    List<Member> getMembers(MemberCommand command);
}
