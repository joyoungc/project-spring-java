package io.joyoungc.domain.member;

import java.util.List;

/**
 * Port
 */
public interface MemberRepository {

    Member findById(Long memberId);

    Long save(Member member);

    List<Member> findMembers(Grade grade);
}
