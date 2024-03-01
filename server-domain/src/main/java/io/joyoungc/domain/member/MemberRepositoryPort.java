package io.joyoungc.domain.member;

import java.util.List;

/**
 * Persistence Port
 */
public interface MemberRepositoryPort {

    Member findById(Long memberId);

    Long save(Member member);

    List<Member> findMembers(Grade grade);
}
