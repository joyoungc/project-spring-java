package io.joyoungc.api.request;

import io.joyoungc.domain.common.constant.Constants;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class OrderCacheResponse implements Serializable {
    private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;
    private Long id;
    private String name;
}
