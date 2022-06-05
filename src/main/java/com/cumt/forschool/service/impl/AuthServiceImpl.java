package com.cumt.forschool.service.impl;

import com.cumt.forschool.bo.AccessToken;
import com.cumt.forschool.entity.MyUserDetail;
import com.cumt.forschool.service.AuthService;
import com.cumt.forschool.utils.JWTokenUtil;
import com.cumt.forschool.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @Author: ahui
 * @date: 2022/2/12 - 22:21
 */
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTokenUtil jwTokenUtil;


    @Override
    public ResultVO login(String username, String password) {
        log.debug("进入了login方法");
        // 1. 创建UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken usernamePasswordAuthentication = new UsernamePasswordAuthenticationToken(username, password);
        // 2. 认证 这里会调用loadUsername 并且会将密码与数据库的进行比对
        Authentication authentication = this.authenticationManager.authenticate(usernamePasswordAuthentication);
        // 3. 将认证后的信息保存至上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 4. 生成自定义的token
        log.debug("认证后的信息: "+authentication.getPrincipal());
        log.debug("认证后的信息: "+authentication.getName());
        AccessToken token = jwTokenUtil.generateToken(authentication.getName());

        MyUserDetail userDetail = (MyUserDetail) authentication.getPrincipal();

        // 可以选择放入缓存进行优化
        if (token == null){
            return ResultVO.fail("登录失败");
        }
        return ResultVO.ok(token);
    }

    @Override
    public ResultVO logout() {
        //如果放入了缓存要记得清楚
        SecurityContextHolder.clearContext();
        return ResultVO.ok("退出成功");
    }

    @Override
    public ResultVO refreshToken(String token) {
        AccessToken accessToken = jwTokenUtil.refreshToken(token);
        //如果存进缓存记得要清除

        return ResultVO.ok(accessToken);
    }
}
