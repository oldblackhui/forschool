package com.cumt.forschool.vo;

import lombok.Data;

/**
 * @Author: ahui
 * @date: 2022/3/19 - 16:12
 */
@Data
public class SimpleApplyVO {
    //可能是组织id 也可能是某个房间的id
    private String id;
    private int status;
}