package io.joyoungc.infrastructure.cache.model;

import io.joyoungc.domain.common.constant.Constants;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class MemberCache implements Serializable {
    @Serial
    private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;
    private Long id;
    private String name;
}
