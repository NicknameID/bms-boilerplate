package tech.mufeng.boilerplate.bms.user.center.api;

public interface AuthenticateService {
    // 校验token是否有效
    boolean verifyToken(String token);
}
