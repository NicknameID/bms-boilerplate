package tech.mufeng.boilerplate.bms.user.center.common.exception;

import tech.mufeng.boilerplate.bms.common.exception.CustomException;

public class LoginException extends CustomException {
    public LoginException() {
        super("登录异常");
    }
}
