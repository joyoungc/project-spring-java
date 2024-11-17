package io.joyoungc.application.input;

import io.joyoungc.application.output.MemberRepositoryPort;
import io.joyoungc.domain.model.member.Member;
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
public class MemberService implements MemberUseCase {

    private final MemberRepositoryPort memberRepositoryPort;

    @Override
    @Transactional
    public Long createMember(Member member) {
        return memberRepositoryPort.save(member);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Member> getMembers(MemberCommand command) {
        return memberRepositoryPort.findMembers(command.grade());
    }


    @Override
    @Transactional(readOnly = true)
    public Member getMember(long userId) {
        return memberRepositoryPort.findById(userId);
    }

}
