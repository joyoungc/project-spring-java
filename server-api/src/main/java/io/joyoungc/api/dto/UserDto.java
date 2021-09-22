package io.joyoungc.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/***
 * Created by Aiden Jeong on 2021.09.22
 */
public class UserDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class RequestUser {
        @NotBlank
        private String name;
        @NotBlank
        private String createdBy;

        public RequestUser(String name, String createdBy) {
            this.name = name;
            this.createdBy = createdBy;
        }
    }
}
