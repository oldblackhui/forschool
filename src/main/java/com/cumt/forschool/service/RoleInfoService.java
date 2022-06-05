package com.cumt.forschool.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cumt.forschool.entity.RoleInfo;
import com.cumt.forschool.entity.RoomInfo;

import java.util.List;

/**
 * @Author: ahui
 * @date: 2022/2/11 - 23:05
 */
public interface RoleInfoService extends IService<RoleInfo> {

    //根据学号查询出所有的权限信息
    List<RoleInfo> listRoleByUsername(String username);


}
