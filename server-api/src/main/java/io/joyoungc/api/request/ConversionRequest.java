package io.joyoungc.api.request;

import io.joyoungc.api.configuration.format.annotation.TimestampToDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ConversionRequest {
    @TimestampToDate
    private Date date;

    @TimestampToDate
    private LocalDateTime localDateTime;
}
