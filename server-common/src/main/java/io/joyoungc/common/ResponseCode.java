package io.joyoungc.common;

/***
 * Created by Aiden Jeong on 2021.12.13
 */
public enum ResponseCode {
    SUCCESS("00", "성공"),
    DUPLICATED("01", "중복된 요청");
    private String code;
    private String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
