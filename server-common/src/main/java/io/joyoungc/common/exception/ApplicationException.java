package io.joyoungc.common.exception;

import io.joyoungc.common.ErrorCode;

/***
 * Created by Aiden Jeong on 2022.02.24
 */
public class ApplicationException extends RuntimeException {

    private String errorCode;
    private String errorMessage;

    public ApplicationException(ErrorCode code) {
        super(code.getCode());
        this.errorCode = code.getCode();
    }

    public ApplicationException(ErrorCode code, String message) {
        super(code.getCode() + " " + message);
        this.errorCode = code.getCode();
        this.errorMessage = message;
    }

}
