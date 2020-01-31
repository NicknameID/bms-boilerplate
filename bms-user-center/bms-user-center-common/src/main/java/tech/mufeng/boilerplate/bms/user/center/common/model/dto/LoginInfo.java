package tech.mufeng.boilerplate.bms.user.center.common.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoginInfo {
    private Long uid;

    private String username;

    private String token;

    private String refreshToken;

    private LocalDateTime loginAt;

    private LocalDateTime expireAt;
}
