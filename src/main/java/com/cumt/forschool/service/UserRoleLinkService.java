package com.cumt.forschool.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cumt.forschool.entity.UserRoleLink;
import com.cumt.forschool.vo.ResultVO;

/**
 * @Author: ahui
 * @date: 2022/6/18 - 16:38
 */
public interface UserRoleLinkService extends IService<UserRoleLink> {

    //添加一条用户角色对应信息
    ResultVO addUserRoleLink(String username, int roleId);
}
