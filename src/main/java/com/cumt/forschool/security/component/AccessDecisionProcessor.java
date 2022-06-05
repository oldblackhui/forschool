package com.cumt.forschool.security.component;

import com.cumt.forschool.bo.PermissionBo;
import com.cumt.forschool.entity.MyUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * @Author: ahui
 * @date: 2022/2/13 - 18:26
 */
@Slf4j
public class AccessDecisionProcessor implements AccessDecisionVoter<FilterInvocation> {

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    AntPathMatcher antPathMatcher;


    @Override
    public int vote(Authentication authentication, FilterInvocation object, Collection<ConfigAttribute> attributes) {
        assert authentication != null;
        assert object != null;

        //拿到当前的请求uri
        String requestURI = object.getRequestUrl();
        log.info("路径为: "+requestURI);
        if(requestURI.equals("/api/auth/login")){ // 直接放行的请求可以放在这里
            return ACCESS_GRANTED;
        }
        String[] s = requestURI.split("\\?");
        if(s.length>0){
            requestURI = s[0];
        }

        String method = object.getRequest().getMethod();


        String key = method + ":"+requestURI;
        log.info("key: "+key);
        //如果缓存中没有此权限就是没有受到保护的API, 进行弃权
        ///redis
        PermissionBo permissionBo = (PermissionBo) redisTemplate.opsForValue().get(key);
        //antPathMatcher.match()
        if (permissionBo == null ){
            log.info("此路径不受保护 ");
            return ACCESS_ABSTAIN;
        }

        // 拿到当前用户所具有的权限
        List<String> roles = ((MyUserDetail)authentication.getPrincipal()).getRoles();
        if (roles.contains(permissionBo.getRoleName())){
            return ACCESS_GRANTED;
        }else {
            return ACCESS_DENIED;
        }

    }



    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }


}
