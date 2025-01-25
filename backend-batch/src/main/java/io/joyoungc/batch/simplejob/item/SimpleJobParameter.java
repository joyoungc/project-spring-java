package io.joyoungc.batch.simplejob.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
@ToString
public class SimpleJobParameter {
    @Value("#{jobParameters[name]}")
    private String name;

    @Value("#{jobParameters[age]}")
    private Integer age;
}
