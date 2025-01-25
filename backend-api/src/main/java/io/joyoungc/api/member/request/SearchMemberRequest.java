package io.joyoungc.api.member.request;

import io.joyoungc.domain.model.member.Grade;
import lombok.*;

@Getter
@Setter
public class SearchMemberRequest {
    private Grade grade;

    public SearchMemberRequest(Grade grade) {
        this.grade = grade;
    }
}
