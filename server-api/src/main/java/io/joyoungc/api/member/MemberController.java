package io.joyoungc.api.member;

import io.joyoungc.api.member.request.CreateMemberRequest;
import io.joyoungc.api.member.request.SearchMemberRequest;
import io.joyoungc.api.member.response.MemberResponse;
import io.joyoungc.domain.common.CommonResponse;
import io.joyoungc.domain.common.constant.ResponseCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    public List<MemberResponse> getMembers(SearchMemberRequest search) {
        return memberService.getMembers(search);
    }

    /**
     * 회원 등록
     *
     * @param dto
     * @return
     */
    @PostMapping
    public CommonResponse createMember(@RequestBody @Valid CreateMemberRequest dto) {
        Long memberId = memberService.createMember(dto);
        return CommonResponse.of(ResponseCode.SUCCESS, "memberId : " + memberId);
    }

    /**
     * 회원 조회
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public MemberResponse getMember(@PathVariable("id") long id) {
        return memberService.getMember(id);
    }

}
