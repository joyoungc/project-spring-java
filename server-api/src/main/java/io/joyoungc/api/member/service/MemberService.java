package io.joyoungc.api.member.service;

import io.joyoungc.api.member.dto.MemberDto;
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

    @Transactional(readOnly = true)
    public MemberDto.ResponseUser getMember(long userId) {
        Member member = memberRepository.getById(userId);
        return modelMapper.map(member, MemberDto.ResponseUser.class);
    }

    @Transactional(readOnly = true)
    public List<MemberDto.ResponseUser> getMembers() {
        List<Member> all = memberRepository.findAll();
        return all.stream().map(o -> modelMapper.map(o, MemberDto.ResponseUser.class)).collect(Collectors.toList());
    }

//    @Cacheable(value = CACHE_USER_LIST, cacheManager = ServerCacheConfig.CACHE_JDK_MANAGER)
//    @Transactional(readOnly = true)
//    public MemberDto.ResponseUser2 getUserByJdkCache(long userId) {
//        User user = memberRepository.getById(userId);
//        return modelMapper.map(user, MemberDto.ResponseUser2.class);
//    }

    @Transactional
    public Long createMember(MemberDto.RequestUser dto) {
        Member member = modelMapper.map(dto, Member.class);
        Member save = memberRepository.save(member);
        return save.getId();
    }

}
