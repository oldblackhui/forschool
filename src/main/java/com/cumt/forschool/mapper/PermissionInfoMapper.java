package com.cumt.forschool.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cumt.forschool.bo.PermissionBo;
import com.cumt.forschool.entity.PermissionInfo;

import java.util.List;

/**
 * @Author: ahui
 * @date: 2022/2/11 - 23:08
 */
public interface PermissionInfoMapper extends BaseMapper<PermissionInfo> {

    List<PermissionBo> listAllPermissionInfo();
}
