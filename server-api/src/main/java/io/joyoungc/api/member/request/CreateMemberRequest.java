package io.joyoungc.api.member.request;

import io.joyoungc.domain.member.Grade;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
