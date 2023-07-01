package io.joyoungc.api.member.service;

import io.joyoungc.api.member.dto.MemberDto;
import io.joyoungc.common.CommonError;
import io.joyoungc.common.exception.ApplicationException;
import io.joyoungc.data.shop.domain.Member;
import io.joyoungc.data.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/***
 * Created by Aiden Jeong on 2021.11.14
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public Long createMember(MemberDto.RequestUser dto) {
        Member member = modelMapper.map(dto, Member.class);
        Member save = memberRepository.save(member);
        return save.getId();
    }

    @Transactional(readOnly = true)
    public MemberDto.ResponseUser getMember(long userId) {
        Member member =
                memberRepository.findById(userId).orElseThrow(() -> new ApplicationException(CommonError.COMMON_NOT_FOUND));
        return modelMapper.map(member, MemberDto.ResponseUser.class);
    }

    @Transactional(readOnly = true)
    public List<MemberDto.ResponseUser> getMembers(MemberDto.Search search) {
        List<Member> all = memberRepository.findMembers(search.getGrade());
        return all.stream().map(o -> modelMapper.map(o, MemberDto.ResponseUser.class)).collect(Collectors.toList());
    }

//    @Cacheable(value = CACHE_USER_LIST, cacheManager = ServerCacheConfig.CACHE_JDK_MANAGER)
//    @Transactional(readOnly = true)
//    public MemberDto.ResponseUser2 getUserByJdkCache(long userId) {
//        User user = memberRepository.getById(userId);
//        return modelMapper.map(user, MemberDto.ResponseUser2.class);
//    }

}
