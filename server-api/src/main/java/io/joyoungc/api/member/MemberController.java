package io.joyoungc.api.member;

import io.joyoungc.api.member.mapper.MemberMapper;
import io.joyoungc.api.member.request.CreateMemberRequest;
import io.joyoungc.api.member.request.SearchMemberRequest;
import io.joyoungc.api.member.response.MemberResponse;
import io.joyoungc.application.input.MemberCommand;
import io.joyoungc.application.input.MemberService;
import io.joyoungc.application.input.MemberUseCase;
import io.joyoungc.domain.model.common.CommonResponse;
import io.joyoungc.domain.constant.ResponseCode;
import io.joyoungc.domain.model.member.Member;
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

    private final MemberUseCase memberService;

    /**
     * 회원 전체 조회
     *
     * @return
     */
    @GetMapping
    public List<MemberResponse> getMembers(SearchMemberRequest search) {
        MemberCommand command = MemberMapper.INSTANCE.toSearch(search);
        return MemberMapper.INSTANCE.toMemberResponseList(memberService.getMembers(command));
    }

    /**
     * 회원 등록
     *
     * @param dto
     * @return
     */
    @PostMapping
    public CommonResponse createMember(@RequestBody @Valid CreateMemberRequest dto) {
        Member member = MemberMapper.INSTANCE.toMember(dto);
        Long memberId = memberService.createMember(member);
        return CommonResponse.of(ResponseCode.SUCCESS, "memberId : " + memberId);
    }

    /**
     * 회원 조회
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public MemberResponse getMember(@PathVariable("id") long id) {
        return MemberMapper.INSTANCE.toMemberResponse(memberService.getMember(id)) ;
    }

}
