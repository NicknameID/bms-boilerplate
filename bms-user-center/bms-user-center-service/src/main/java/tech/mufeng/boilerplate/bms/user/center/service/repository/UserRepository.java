package tech.mufeng.boilerplate.bms.user.center.service.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;
import tech.mufeng.boilerplate.bms.user.center.common.model.entity.User;
import tech.mufeng.boilerplate.bms.user.center.service.mapper.UserMapper;

@Repository
public class UserRepository extends ServiceImpl<UserMapper, User> {
}
