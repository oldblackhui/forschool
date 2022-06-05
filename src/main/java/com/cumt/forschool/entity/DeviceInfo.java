package com.cumt.forschool.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: ahui
 * @date: 2022/2/22 - 19:29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DeviceInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "device_id",type = IdType.UUID)
    private String deviceId;

    @TableField(value = "device_name")
    private String devicename;

    @TableField(value = "organization_id")
    private String organizationId;

    private Integer num;

    private String info;

    private Integer deleted;

    private LocalDateTime create_time;
}