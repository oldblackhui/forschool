package com.cumt.forschool.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cumt.forschool.entity.RoleInfo;
import com.cumt.forschool.entity.UserInfo;
import com.cumt.forschool.entity.UserRoleLink;
import com.cumt.forschool.exception.ApiException;
import com.cumt.forschool.mapper.UserRoleLinkMapper;
import com.cumt.forschool.service.RoleInfoService;
import com.cumt.forschool.service.UserInfoService;
import com.cumt.forschool.service.UserRoleLinkService;
import com.cumt.forschool.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: ahui
 * @date: 2022/6/18 - 16:39
 */
@Service
public class UserRoleLinkServiceImpl extends ServiceImpl<UserRoleLinkMapper, UserRoleLink> implements UserRoleLinkService {

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    RoleInfoService roleInfoService;


    @Override
    public ResultVO addUserRoleLink(String username, int roleId) {
        UserInfo user = userInfoService.getById(username);
        RoleInfo role = roleInfoService.getOne(new QueryWrapper<RoleInfo>().eq("role_id",roleId));
        if (user == null || role == null){
            return ResultVO.fail("请输入正确的学号或id");
        }
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("username",username);
        wrapper.eq("role_id",roleId);
        wrapper.eq("deleted",0);
        UserRoleLink res = baseMapper.selectOne(wrapper);
        if (res != null){
            return ResultVO.fail("请勿重复插入");
        }
        UserRoleLink userRoleLink = new UserRoleLink();

        userRoleLink.setRoleId(roleId);
        userRoleLink.setUsername(username);
        int i = baseMapper.insert(userRoleLink);
        return ResultVO.ok("插入user role对应信息成功");
    }
}
