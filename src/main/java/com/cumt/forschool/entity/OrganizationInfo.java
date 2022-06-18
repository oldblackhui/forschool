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
 * @date: 2022/2/14 - 23:02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrganizationInfo implements Serializable {

    @TableId(value = "organization_id",type = IdType.UUID)
    private String organizationId;

    @TableField(value = "manager_id")
    private String managerId;

    private String organizationName;

    private String leader;

    private String phone;

    private String introduction;

    private String location;

    private Integer deleted;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @TableField(value = "role_name")
    private String roleName;
}
