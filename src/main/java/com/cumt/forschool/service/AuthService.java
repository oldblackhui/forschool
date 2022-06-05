package com.cumt.forschool.service;

import com.cumt.forschool.vo.ResultVO;

/**
 * @Author: ahui
 * @date: 2022/2/12 - 22:21
 */
public interface AuthService {

    ResultVO login(String username, String password);

    ResultVO logout();

    ResultVO refreshToken(String token);

}
