package com.cumt.forschool.utils;

import com.cumt.forschool.bo.AccessToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Date;
import cn.hutool.core.date.DateUtil;

/**
 * @Author: ahui
 * @date: 2022/2/12 - 18:07
 */
@Slf4j
@Component
public class JWTokenUtil {

    public final String TOKEN_HEADER = "Authorization";
    public final String TOKEN_PREFIX = "Bearer ";

    //JWT密钥
    public final String APISECRETKEY = "jwttokentest";

    //设置过期时间
    public Duration expiration = Duration.ofDays(2);

    //从请求中拿到token
    public String getToken(HttpServletRequest request) {
        return request.getHeader(TOKEN_HEADER);
    }

    //生成token 以用户名作为token
    public AccessToken generateToken(String username){
        Date now = new Date();
        Date expiryDate = new Date(System.currentTimeMillis()+expiration.toMillis());
        String token = TOKEN_PREFIX + Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, APISECRETKEY)
                .compact();
        AccessToken accessToken = new AccessToken(username,token,expiryDate);
        return accessToken;
    }


    /**
     * 刷新token
     * 过滤器会对请求进行验证，所以这里可以不必验证
     *
     * @param oldToken 带tokenHead的token
     */
    public AccessToken refreshToken(String oldToken) {
        String token = oldToken.substring(TOKEN_HEADER.length());

        // token反解析
        Claims claims = getClaimsFromToken(token);

        //如果token在30分钟之内刚刷新过，返回原token
        if (tokenRefreshJustBefore(claims)) {

            return new AccessToken(claims.getSubject(),token,claims.getExpiration());
        } else {
            return generateToken(claims.getSubject());
        }
    }


    //验证token是否有效(正确或者过期)
    public boolean validateToken(String token, String username){
        Claims claims = getClaimsFromToken(token);
        return claims.getSubject().equals(username) && ! isTokenExpired(claims);
    }

    /**
     * 从token中拿到负载信息
     */
    public Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(APISECRETKEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("JWT反解析失败, token已过期或不正确, token : {}", token);
        }
        return claims;
    }


    /**
     * 判断token在指定时间内是否刚刚刷新过
     */
    private boolean tokenRefreshJustBefore(Claims claims) {
        Date refreshDate = new Date();
        //刷新时间在创建时间的指定时间内
        if (refreshDate.after(claims.getExpiration()) && refreshDate.before(DateUtil.offsetSecond(claims.getExpiration(), 1800))) {
            return true;
        }
        return false;
    }

    /**
     * 判断token是否已经过期
     */
    private boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

}
