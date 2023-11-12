package io.joyoungc.api.service;

import io.joyoungc.api.mapper.MemberMapper;
import io.joyoungc.api.request.CreateMemberRequest;
import io.joyoungc.api.request.SearchMemberRequest;
import io.joyoungc.api.response.MemberResponse;
import io.joyoungc.domain.member.Member;
import io.joyoungc.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/***
 * Created by Aiden Jeong on 2021.11.14
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long createMember(CreateMemberRequest dto) {
        Member member = MemberMapper.INSTANCE.toMember(dto);
        return memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public MemberResponse getMember(long userId) {
        Member member = memberRepository.findById(userId);
        return MemberMapper.INSTANCE.toMemberResponse(member);
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> getMembers(SearchMemberRequest search) {
        List<Member> all = memberRepository.findMembers(search.getGrade());
        return MemberMapper.INSTANCE.toMemberResponseList(all);
    }

//    @Cacheable(value = CACHE_USER_LIST, cacheManager = ServerCacheConfig.CACHE_JDK_MANAGER)
//    @Transactional(readOnly = true)
//    public OrderCacheResponse getUserByJdkCache(long userId) {
//        User user = memberRepository.getById(userId);
//        return modelMapper.map(user, OrderCacheResponse.class);
//    }

}