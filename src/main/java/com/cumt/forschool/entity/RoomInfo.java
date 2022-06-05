package com.cumt.forschool.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: ahui
 * @date: 2022/2/15 - 18:35
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RoomInfo implements Serializable {

    @TableId(value = "room_id", type = IdType.UUID)
    private String roomId;

    @TableField(value = "organization_id")
    private String organizationId;

    @TableField(value = "room_name")
    private String roomName;

    private String leader;

    private String introduction;

    private Integer deleted;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}