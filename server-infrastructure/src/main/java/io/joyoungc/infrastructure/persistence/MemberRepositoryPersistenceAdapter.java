package io.joyoungc.infrastructure.persistence;

import io.joyoungc.domain.common.constant.CommonError;
import io.joyoungc.domain.common.exception.ApplicationException;
import io.joyoungc.domain.member.Grade;
import io.joyoungc.domain.member.Member;
import io.joyoungc.domain.member.MemberRepositoryPort;
import io.joyoungc.infrastructure.persistence.configuration.PersistenceAdapter;
import io.joyoungc.infrastructure.persistence.entity.MemberEntity;
import io.joyoungc.infrastructure.persistence.mapper.MemberMapper;
import io.joyoungc.infrastructure.persistence.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class MemberRepositoryPersistenceAdapter implements MemberRepositoryPort {

    private final MemberJpaRepository memberJpaRepository;
    private final MemberMapper memberMapper;

    @Override
    public Member findById(Long memberId) {
        MemberEntity memberEntity = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new ApplicationException(CommonError.COMMON_NOT_FOUND));
        return memberMapper.toMember(memberEntity);
    }

    @Override
    @Transactional
    public Long save(Member member) {
        MemberEntity memberEntity = new MemberEntity(member.getName(),
                Grade.valueOf(member.getGrade().name()));
        return memberJpaRepository.save(memberEntity).getId();
    }

    @Override
    public List<Member> findMembers(Grade grade) {
        List<MemberEntity> members = memberJpaRepository.findMembers(grade);
        return memberMapper.toMemberList(members);
    }
}
