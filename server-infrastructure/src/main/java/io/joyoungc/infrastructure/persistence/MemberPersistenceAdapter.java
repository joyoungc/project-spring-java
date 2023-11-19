package io.joyoungc.infrastructure.persistence;

import io.joyoungc.domain.common.constant.CommonError;
import io.joyoungc.domain.common.exception.ApplicationException;
import io.joyoungc.domain.member.Grade;
import io.joyoungc.domain.member.Member;
import io.joyoungc.domain.member.MemberRepository;
import io.joyoungc.infrastructure.cache.ServerCacheConfig;
import io.joyoungc.infrastructure.cache.model.MemberCache;
import io.joyoungc.infrastructure.persistence.entity.MemberEntity;
import io.joyoungc.infrastructure.persistence.mapper.MemberMapper;
import io.joyoungc.infrastructure.persistence.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static io.joyoungc.infrastructure.cache.CacheCodes.Cache.Constants.CACHE_USER_LIST;

@Repository
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;
    private MemberMapper memberMapper = MemberMapper.INSTANCE;

    @Override
    public Member findById(Long memberId) {
        MemberEntity memberEntity = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new ApplicationException(CommonError.COMMON_NOT_FOUND));
        return memberMapper.toMember(memberEntity);
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
        return memberMapper.toMemberList(members);
    }

    @Cacheable(value = CACHE_USER_LIST, cacheManager = ServerCacheConfig.CACHE_JDK_MANAGER)
    public MemberCache getMemberByJdkCache(long memberId) {
        MemberEntity memberEntity = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new ApplicationException(CommonError.COMMON_NOT_FOUND));
        return memberMapper.toMemberCache(memberEntity);
    }
}
