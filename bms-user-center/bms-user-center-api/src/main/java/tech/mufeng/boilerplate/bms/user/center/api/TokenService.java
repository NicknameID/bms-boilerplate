package tech.mufeng.boilerplate.bms.user.center.api;

import java.time.Duration;

public interface TokenService {
    // 签发新的token
    String generateToken(Long uid, Duration expire);

    boolean verifyToken(String token);

    boolean hasToken(String token);

    boolean canRefreshToken(String refreshToken);

    String refreshToken(String refreshToken);

    void clearToken(String token);
}
