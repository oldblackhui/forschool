package com.cumt.forschool.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cumt.forschool.bo.PermissionBo;
import com.cumt.forschool.entity.PermissionInfo;

import java.util.List;

/**
 * @Author: ahui
 * @date: 2022/2/11 - 23:06
 */
public interface PermissionInfoService extends IService<PermissionInfo> {
    List<PermissionBo> getAllPermissionInfo();
}
