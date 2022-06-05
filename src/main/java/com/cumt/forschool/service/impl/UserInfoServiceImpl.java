package com.cumt.forschool.service.impl;

import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cumt.forschool.entity.UserInfo;
import com.cumt.forschool.mapper.UserInfoMapper;
import com.cumt.forschool.service.UserInfoService;
import com.cumt.forschool.vo.ResultVO;
import org.springframework.stereotype.Service;

import javax.xml.transform.Result;

/**
 * @Author: ahui
 * @date: 2022/2/11 - 23:03
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {


    @Override
    public ResultVO getUserInfo(String username) {
        UserInfo userInfo = baseMapper.selectById(username);
        if (userInfo == null || userInfo.getDeleted() == 1) {
            return ResultVO.fail("该用户不存在");
        }
        userInfo.setPassword("");
        return ResultVO.ok(userInfo);
    }
}
