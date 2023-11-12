package io.joyoungc.api.infrastructure.persistence;

import io.joyoungc.api.application.mapper.MemberMapper;
import io.joyoungc.api.infrastructure.repository.MemberJpaRepository;
import io.joyoungc.data.jpa.domain.MemberEntity;
import io.joyoungc.domain.common.exception.ApplicationException;
import io.joyoungc.domain.constant.CommonError;
import io.joyoungc.domain.member.Grade;
import io.joyoungc.domain.member.Member;
import io.joyoungc.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Member findById(Long memberId) {
        MemberEntity memberEntity = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new ApplicationException(CommonError.COMMON_NOT_FOUND));
        return MemberMapper.INSTANCE.toMember(memberEntity);
    }

    @Override
    public Long save(Member member) {
        MemberEntity memberEntity = new MemberEntity(member.getName(),
                Grade.valueOf(member.getGrade().name()));
        return memberJpaRepository.save(memberEntity).getId();
    }

    @Override
    public List<Member> findMembers(Grade grade) {
        List<MemberEntity> members = memberJpaRepository.findMembers(grade);
        return MemberMapper.INSTANCE.toMemberList(members);
    }
}
