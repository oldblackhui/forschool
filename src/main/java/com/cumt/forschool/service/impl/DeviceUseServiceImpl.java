package com.cumt.forschool.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cumt.forschool.entity.ApplyForRent;
import com.cumt.forschool.entity.DeviceUse;
import com.cumt.forschool.mapper.DeviceUseMapper;
import com.cumt.forschool.service.DeviceInfoService;
import com.cumt.forschool.service.DeviceUseService;
import com.cumt.forschool.vo.ResultVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: ahui
 * @date: 2022/2/22 - 19:48
 */
@Service
public class DeviceUseServiceImpl extends ServiceImpl<DeviceUseMapper, DeviceUse> implements DeviceUseService {

    @Override
    public int findUsingDeviceNum(String deviceId) {
        QueryWrapper<DeviceUse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("device_id",deviceId)
                    .eq("deleted",0);
        List<DeviceUse> deviceUses = baseMapper.selectList(queryWrapper);
        int usingNum = 0;
        for (DeviceUse deviceUs : deviceUses) {
            usingNum += deviceUs.getNum();
        }

        return usingNum;
    }

    @Override
    public int addRoomUse(DeviceUse deviceUse) {

        if (deviceUse.getUsername() == null){
            throw new ApiException("请确定学号");
        }
        int insert = baseMapper.insert(deviceUse);

        return insert;
    }


}
