package io.joyoungc.application.output;

import io.joyoungc.domain.model.member.Grade;
import io.joyoungc.domain.model.member.Member;

import java.util.List;

/**
 * Persistence Port
 */
public interface MemberRepositoryPort {

    Member findById(Long memberId);

    Long save(Member member);

    List<Member> findMembers(Grade grade);
}
