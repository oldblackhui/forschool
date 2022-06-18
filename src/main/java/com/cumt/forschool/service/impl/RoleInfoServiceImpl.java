package com.cumt.forschool.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cumt.forschool.entity.RoleInfo;
import com.cumt.forschool.entity.UserInfo;
import com.cumt.forschool.mapper.RoleInfoMapper;
import com.cumt.forschool.mapper.UserInfoMapper;
import com.cumt.forschool.service.RoleInfoService;
import com.cumt.forschool.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.awt.*;
import java.util.List;

/**
 * @Author: ahui
 * @date: 2022/2/11 - 23:07
 */
@Slf4j
@Service
public class RoleInfoServiceImpl extends ServiceImpl<RoleInfoMapper, RoleInfo> implements RoleInfoService {

    @Override
    public List<RoleInfo> listRoleByUsername(String username) {
        if (StringUtils.isEmpty(username)) {
            return null;
        }

        return baseMapper.listRoleByUsername(username);
    }

    @Override
    public int addRoleAboutOrgan(String roleName) {
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setRoleName(roleName);
        roleInfo.setRoleMark("组织管理员");
        int insert = baseMapper.insert(roleInfo);
        log.info(roleName+"管理员主键: "+roleInfo.getRoleId());
        return insert;
    }

    @Override
    public List<RoleInfo> listALLRoleInfo() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("deleted",0);
        List<RoleInfo> list = baseMapper.selectList(wrapper);
        return list;
    }

    @Override
    public int addRoleByUsername(String username) {

        return 0;
    }
}
