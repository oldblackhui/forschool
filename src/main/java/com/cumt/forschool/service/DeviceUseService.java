package com.cumt.forschool.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cumt.forschool.entity.ApplyForRent;
import com.cumt.forschool.entity.DeviceUse;
import com.cumt.forschool.vo.ResultVO;

/**
 * @Author: ahui
 * @date: 2022/2/22 - 19:48
 */
public interface DeviceUseService extends IService<DeviceUse> {

    // 统计借出去的设备数量
    int findUsingDeviceNum(String deviceId);

    int addRoomUse(DeviceUse deviceUse);


}
