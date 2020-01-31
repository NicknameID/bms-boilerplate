package tech.mufeng.boilerplate.bms.common.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreatedAtBaseEntity {
    protected LocalDateTime createdAt;
}
