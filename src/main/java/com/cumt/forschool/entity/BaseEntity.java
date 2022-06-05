package com.cumt.forschool.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: ahui
 * @date: 2022/2/11 - 22:46
 */
@Data
public class BaseEntity implements Serializable {


    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @TableField("create_time")
    private Date createTime;

    /*@TableField(value = "update_time")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;*/

    @TableField("deleted")
    private Integer deleted;

}
