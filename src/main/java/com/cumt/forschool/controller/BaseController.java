package com.cumt.forschool.controller;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cumt.forschool.entity.MyUserDetail;
import com.cumt.forschool.entity.UserRoleLink;
import com.cumt.forschool.exception.ApiException;
import com.cumt.forschool.service.*;
import com.cumt.forschool.utils.JWTokenUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;

/**
 * @date 2021/10/29 - 15:38
 */
@Slf4j
public class BaseController {
    @Autowired
    HttpServletRequest request;

    @Autowired
    JWTokenUtil jwTokenUtil;

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    AuthService authService;

    @Autowired
    OrganizationInfoService organizationInfoService;

    @Autowired
    RoomUseService roomUseService;

    @Autowired
    RoomInfoService roomInfoService;

    @Autowired
    DeviceUseService deviceUseService;

    @Autowired
    DeviceInfoService deviceInfoService;

    @Autowired
    ApplyForRentService applyForRentService;

    @Autowired
    RoleInfoService roleInfoService;

    @Autowired
    UserRoleLinkService userRoleLinkService;


    protected String getLoginUser(){
        String token = request.getHeader("Authorization");
        if(StringUtils.isEmpty(token)){
            throw new ApiException("token获取失败");
        }

        Claims claim = jwTokenUtil.getClaimsFromToken(token);
        String username = claim.getSubject();
        if(StringUtils.isEmpty(username)){
            throw new ApiException("用户名获取失败,用户为空");
        }
        return username;
    }

    protected String getUsernameFromAuth(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        log.info("getUsernameFromAUTH   ==== "+authentication.getPrincipal());
        return username;
    }

    protected MyUserDetail getAuthUser(){
        String token = request.getHeader("Authorization");
        if(StringUtils.isEmpty(token)){
            throw new ApiException("token获取失败");
        }

        Claims claim = jwTokenUtil.getClaimsFromToken(token);
        String username = claim.getSubject();
        MyUserDetail userDetail = (MyUserDetail) SecurityContextHolder.getContext().getAuthentication();
        return userDetail;
    }
    protected Page getDefaultPage(){
        Page defaultPage = new Page(1,5);
        return defaultPage;
    }
}
