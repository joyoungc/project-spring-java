package io.joyoungc.api.request;

import io.joyoungc.domain.member.Grade;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class CreateMemberRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String createdBy;

    private Grade grade = Grade.BASIC;

    public CreateMemberRequest(String name, String createdBy) {
        this.name = name;
        this.createdBy = createdBy;
    }
}
