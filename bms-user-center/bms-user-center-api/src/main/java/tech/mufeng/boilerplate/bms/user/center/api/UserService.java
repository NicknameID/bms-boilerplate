package tech.mufeng.boilerplate.bms.user.center.api;

import tech.mufeng.boilerplate.bms.user.center.common.model.entity.User;

import java.util.List;

public interface UserService {
    User getUser(Long uid);

    User getUser(String username);

    List<User> listUsers();

    // 增加新用户
    User addUser(String username, String password);

    // 重设密码
    boolean resetPassword(Long uid, String password);

    // 停用用户
    boolean deactivate(Long uid);

    // 启用用户
    boolean active(Long uid);
}
