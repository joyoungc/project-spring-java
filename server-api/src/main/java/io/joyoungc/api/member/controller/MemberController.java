package io.joyoungc.api.member.controller;

import io.joyoungc.api.member.dto.MemberDto;
import io.joyoungc.api.member.service.MemberService;
import io.joyoungc.common.CommonResponse;
import io.joyoungc.common.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/***
 * Created by Aiden Jeong on 2021.09.22
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원 전체 조회
     *
     * @return
     */
    @GetMapping
    public List<MemberDto.ResponseUser> getMembers(MemberDto.Search search) {
        return memberService.getMembers(search);
    }

    /**
     * 회원 등록
     *
     * @param dto
     * @return
     */
    @PostMapping
    public CommonResponse createMember(@RequestBody @Valid MemberDto.RequestUser dto) {
        Long memberId = memberService.createMember(dto);
        return CommonResponse.of(ResponseCode.SUCCESS, "memberId : " + memberId);
    }

    /**
     * 회원 조회
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public MemberDto.ResponseUser getMember(@PathVariable long id) {
        return memberService.getMember(id);
    }

}
