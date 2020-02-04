package tech.mufeng.boilerplate.bms.user.center.rpc.server.service;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import tech.mufeng.boilerplate.bms.user.center.api.LoginService;
import tech.mufeng.boilerplate.bms.user.center.common.exception.LoginException;
import tech.mufeng.boilerplate.bms.user.center.common.exception.TokenInvalidException;
import tech.mufeng.boilerplate.bms.user.center.common.model.dto.LoginInfo;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private AuthenticationManager authenticationManager;

    @Override
    public LoginInfo login(String username, String password) throws LoginException {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(upToken);
        }catch (AuthenticationException e) {
            throw new LoginException();
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return null;
    }

    @Override
    public boolean logout(String token) {
        return false;
    }

    @Override
    public LoginInfo refreshToken(String refreshToken) throws TokenInvalidException {
        return null;
    }
}
