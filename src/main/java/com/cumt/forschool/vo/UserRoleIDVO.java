package com.cumt.forschool.vo;

import lombok.Data;

/**
 * @Author: ahui
 * @date: 2022/6/18 - 16:52
 */
@Data
public class UserRoleIDVO {
    //可能是组织id 也可能是某个房间的id
    private String username;
    private int roleId;
}