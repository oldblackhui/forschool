package com.cumt.forschool.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cumt.forschool.entity.RoleInfo;
import com.cumt.forschool.entity.UserInfo;
import com.cumt.forschool.mapper.RoleInfoMapper;
import com.cumt.forschool.mapper.UserInfoMapper;
import com.cumt.forschool.service.RoleInfoService;
import com.cumt.forschool.service.UserInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: ahui
 * @date: 2022/2/11 - 23:07
 */
@Service
public class RoleInfoServiceImpl extends ServiceImpl<RoleInfoMapper, RoleInfo> implements RoleInfoService {

    @Override
    public List<RoleInfo> listRoleByUsername(String username) {
        if (StringUtils.isEmpty(username)) {
            return null;
        }

        return baseMapper.listRoleByUsername(username);
    }
}
