package tech.mufeng.boilerplate.bms.user.center.api;

import tech.mufeng.boilerplate.bms.user.center.common.exception.LoginException;
import tech.mufeng.boilerplate.bms.user.center.common.exception.TokenInvalidException;
import tech.mufeng.boilerplate.bms.user.center.common.model.dto.LoginInfo;

public interface LoginService {
    LoginInfo login(String username, String password) throws LoginException;

    boolean logout(String token);

    LoginInfo refreshToken(String refreshToken) throws TokenInvalidException;
}
