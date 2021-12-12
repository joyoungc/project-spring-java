package io.joyoungc.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.joyoungc.common.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/***
 * Created by Aiden Jeong on 2021.09.22
 */
public class MemberDto {

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

    @Getter
    @Setter
    public static class ResponseUser {
        private Long id;
        private String name;
        private String createdBy;

        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        private LocalDateTime createdDate;

        private String modifiedBy;

        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        private LocalDateTime modifiedDate;
    }

    @Getter
    @Setter
    public static class ResponseUser2 implements Serializable {
        private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;
        private Long id;
        private String name;
    }
}
