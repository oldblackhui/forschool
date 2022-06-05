package com.cumt.forschool.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cumt.forschool.bo.PermissionBo;
import com.cumt.forschool.entity.PermissionInfo;
import com.cumt.forschool.mapper.PermissionInfoMapper;
import com.cumt.forschool.service.PermissionInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: ahui
 * @date: 2022/2/11 - 23:08
 */
@Service
public class PermissionInfoServiceImpl extends ServiceImpl<PermissionInfoMapper, PermissionInfo> implements PermissionInfoService {
    @Override
    public List<PermissionBo> getAllPermissionInfo() {
        return baseMapper.listAllPermissionInfo();
    }
}
