package tech.mufeng.boilerplate.bms.user.center.rpc.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tech.mufeng.boilerplate.bms.user.center.common.model.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
