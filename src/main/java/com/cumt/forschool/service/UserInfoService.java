package com.cumt.forschool.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cumt.forschool.entity.RoleInfo;
import com.cumt.forschool.entity.UserInfo;
import com.cumt.forschool.vo.ResultVO;

/**
 * @Author: ahui
 * @date: 2022/2/11 - 23:02
 */
public interface UserInfoService extends IService<UserInfo> {

    ResultVO getUserInfo(String username);

}
