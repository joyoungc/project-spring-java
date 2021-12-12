package io.joyoungc.common;

/***
 * Created by Aiden Jeong on 2021.12.13
 */
public class CommonResponse {
    protected String code;
    protected String message;

    public CommonResponse() {}

    public CommonResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static CommonResponse of(ResponseCode code, String message) {
        return new CommonResponse(code.getCode(), message);
    }

    public static CommonResponse of(ResponseCode code) {
        return new CommonResponse(code.getCode(), code.getMessage());
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
