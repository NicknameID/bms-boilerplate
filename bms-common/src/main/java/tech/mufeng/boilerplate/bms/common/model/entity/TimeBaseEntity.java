package tech.mufeng.boilerplate.bms.common.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimeBaseEntity {

    protected LocalDateTime createdAt;

    protected LocalDateTime updatedAt;
}
