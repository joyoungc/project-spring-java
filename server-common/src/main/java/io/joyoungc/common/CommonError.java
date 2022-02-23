package io.joyoungc.common;

/***
 * Created by Aiden Jeong on 2022.02.24
 */
public enum CommonError implements ErrorCode {

    COMMON_INTERNAL_SERVER_ERROR("COM1N0001"),
    COMMON_BAD_REQUEST("COM1N0002"),
    COMMON_UNAUTHORIZED("COM1N0003"),
    COMMON_NOT_FOUND("COM1N0004");

    private final String code;

    CommonError(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }
}
