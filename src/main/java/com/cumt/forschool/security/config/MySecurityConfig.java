package com.cumt.forschool.security.config;

import com.cumt.forschool.security.component.AccessDecisionProcessor;
import com.cumt.forschool.security.component.JwtAuthenticationTokenFilter;
import com.cumt.forschool.security.component.RestAuthenticationEntryPoint;
import com.cumt.forschool.security.component.RestfulAccessDeniedHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.AntPathMatcher;

import java.time.format.DecimalStyle;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: ahui
 * @date: 2022/2/11 - 22:50
 */
@Slf4j
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    //不用认证可以访问的路径(我取消了这个设置 所有请求都要去数据库进行动态设置 )
    public String[] paths = {"/api/auth/login", "/api/anon"};

    //将JWTFilter插入到UsernamePasswordAuthenticationFilter中
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                //放行所有的OPTIONS请求
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers(paths).permitAll()
                .anyRequest().authenticated()   // 其他请求都需要认证后进行访问
                //使用自定义的accessDecisionManager
                .accessDecisionManager(accessDecisionManager())
                .and()
                //添加未登录与权限不足的处理器
                .exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler())
                .authenticationEntryPoint(restAuthenticationEntryPoint())
                .and()
                //将JWTFilter插入到UsernamePasswordAuthenticationFilter前
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .cors()
                .and()
                .csrf().disable()
                //Spring Security中禁用session 只是登录
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


    }

    @Bean
    public AccessDecisionVoter<FilterInvocation> accessDecisionProcessor() {
        return new AccessDecisionProcessor();
    }

    @Bean
    public AccessDecisionManager accessDecisionManager() {
        // 构造一个新的AccessDecisionManager 放入两个投票器 (取消了WebExpressionVoter, 因为这样会造成只要登录所有的接口都可以访问)
        List<AccessDecisionVoter<?>> decisionVoters = Arrays.asList( accessDecisionProcessor());

        return new UnanimousBased(decisionVoters);
    }

    @Bean
    public RestfulAccessDeniedHandler restfulAccessDeniedHandler() {
        return new RestfulAccessDeniedHandler();
    }

    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        return new JwtAuthenticationTokenFilter();
    }

    //身份认证
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    // 密码加密
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // uri匹配工具
    @Bean
    public AntPathMatcher antPathMatcher(){
        return new AntPathMatcher();
    }
}
