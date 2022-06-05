package com.cumt.forschool.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cumt.forschool.entity.RoleInfo;

import java.util.List;

/**
 * @Author: ahui
 * @date: 2022/2/11 - 23:08
 */
public interface RoleInfoMapper extends BaseMapper<RoleInfo> {
    List<RoleInfo> listRoleByUsername(String username);
}
