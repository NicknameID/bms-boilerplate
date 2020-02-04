package tech.mufeng.boilerplate.bms.user.center.rpc.server.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;
import tech.mufeng.boilerplate.bms.user.center.common.model.entity.User;
import tech.mufeng.boilerplate.bms.user.center.rpc.server.mapper.UserMapper;

@Repository
public class UserRepository extends ServiceImpl<UserMapper, User> {
}
