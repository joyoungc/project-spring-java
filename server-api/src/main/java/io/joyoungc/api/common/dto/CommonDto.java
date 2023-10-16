package io.joyoungc.api.common.dto;

import io.joyoungc.api.configuration.format.annotation.TimestampToDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

public class CommonDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class RequestConversion {
        @TimestampToDate
        private Date date;

        @TimestampToDate
        private LocalDateTime localDateTime;
    }
}
