package com.cumt.forschool.security.component;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cumt.forschool.entity.MyUserDetail;
import com.cumt.forschool.entity.UserInfo;
import com.cumt.forschool.security.service.MyUserDetailsService;
import com.cumt.forschool.utils.JWTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: ahui
 * @date: 2022/2/12 - 22:44
 */
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    JWTokenUtil jwTokenUtil;
    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("JWT过滤器拿到token 进行自动登录");
        String authToken = jwTokenUtil.getToken(request);
        if (StringUtils.isNotEmpty(authToken) && authToken.startsWith(jwTokenUtil.TOKEN_PREFIX)){
            //去掉bear 前缀
            authToken = authToken.substring(jwTokenUtil.TOKEN_PREFIX.length());
            // 这里可以加一步redis进行快速认证


            //得到用户名
            String username = jwTokenUtil.getClaimsFromToken(authToken).getSubject();

            if (StringUtils.isNotEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null){
                if(jwTokenUtil.validateToken(authToken,username)){
                    MyUserDetail userDetail = (MyUserDetail) myUserDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail,userDetail.getPassword(),userDetail.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("JWT过滤器校验token通过");
                }
            }
        }

        filterChain.doFilter(request,response);
    }
}
