package com.cumt.forschool.security.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cumt.forschool.entity.MyUserDetail;
import com.cumt.forschool.entity.UserInfo;
import com.cumt.forschool.service.RoleInfoService;
import com.cumt.forschool.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Author: ahui
 * @date: 2022/2/11 - 22:57
 */
@Slf4j
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RoleInfoService roleInfoService;

    @Override // 登录验证
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("开始进行登录验证, 用户名为: "+username);
        // 根据用户名验证用户
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserInfo::getUsername, username);
        UserInfo userInfo = userInfoService.getOne(queryWrapper);

        if (userInfo == null) {
            throw new UsernameNotFoundException("用户名不存在，登陆失败。");
        }

        // 构建UserDetail对象
        MyUserDetail userDetail = new MyUserDetail();
        userDetail.setUserInfo(userInfo);
        // username即学号
        userDetail.setRoleInfoList(roleInfoService.listRoleByUsername(userInfo.getUsername()));

        return userDetail;
    }
}
