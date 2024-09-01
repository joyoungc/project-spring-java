package io.joyoungc.api.member.request;

import io.joyoungc.domain.shop.member.Grade;
import lombok.*;

@Getter
@Setter
public class SearchMemberRequest {
    private Grade grade;

    public SearchMemberRequest(Grade grade) {
        this.grade = grade;
    }
}
