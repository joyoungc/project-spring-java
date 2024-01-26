package io.joyoungc.api.member.request;

import io.joyoungc.domain.member.Grade;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchMemberRequest {
    private Grade grade;
}
