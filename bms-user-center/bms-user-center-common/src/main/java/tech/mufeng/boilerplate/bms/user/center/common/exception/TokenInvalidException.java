package tech.mufeng.boilerplate.bms.user.center.common.exception;

import tech.mufeng.boilerplate.bms.common.exception.CustomException;

public class TokenInvalidException extends CustomException {
    public TokenInvalidException() {
        super("无效的Token");
    }
}
