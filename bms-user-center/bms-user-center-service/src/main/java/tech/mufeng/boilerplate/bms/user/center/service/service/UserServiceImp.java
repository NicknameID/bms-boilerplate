package tech.mufeng.boilerplate.bms.user.center.service.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import tech.mufeng.boilerplate.bms.user.center.api.UserService;
import tech.mufeng.boilerplate.bms.user.center.common.model.entity.User;
import tech.mufeng.boilerplate.bms.user.center.service.repository.UserRepository;
import tech.mufeng.boilerplate.bms.user.center.service.wrapper.IdGeneratorWrapper;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Resource
    private UserRepository userRepository;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private IdGeneratorWrapper idGeneratorWrapper;

    @Override
    public User getUser(Long uid) {
        return userRepository.getById(uid);
    }

    @Override
    public User getUser(String username) {
        LambdaQueryWrapper<User> queryWrapper =
                Wrappers.lambdaQuery( User.builder().username(username).build() );
        return userRepository.getOne(queryWrapper);
    }

    @Override
    public List<User> listUsers() {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByDesc(User::getCreatedAt);
        return userRepository.list(queryWrapper);
    }

    @Override
    public User addUser(String username, String password) {
        User user = User.builder()
                .uid(idGeneratorWrapper.nextId())
                .username(username)
                .password(passwordEncoder.encode(password))
                .active(true)
                .build();
        LocalDateTime now = LocalDateTime.now();
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        userRepository.save(user);
        return getUser(user.getUid());
    }

    @Override
    public boolean resetPassword(Long uid, String password) {
        String cryptoPassword = passwordEncoder.encode(password);
        User user = User.builder()
                .uid(uid)
                .password(cryptoPassword)
                .build();
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.updateById(user);
    }

    @Override
    public boolean deactivate(Long uid) {
        User user = User.builder().uid(uid).active(false).build();
        return userRepository.updateById(user);
    }

    @Override
    public boolean active(Long uid) {
        User user = User.builder().uid(uid).active(true).build();
        return userRepository.updateById(user);
    }
}
