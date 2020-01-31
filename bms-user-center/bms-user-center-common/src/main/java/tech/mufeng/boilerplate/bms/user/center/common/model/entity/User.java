package tech.mufeng.boilerplate.bms.user.center.common.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tech.mufeng.boilerplate.bms.common.model.entity.TimeBaseEntity;

import java.io.Serializable;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class User extends TimeBaseEntity implements Serializable {
    private static final long serialVersionUID = 8972634735535999287L;

    @TableId
    private Long uid;

    private String username;

    private String password;

    private Boolean active;
}
