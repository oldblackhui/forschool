package com.cumt.forschool.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cumt.forschool.DTO.DeviceInfoDTO;
import com.cumt.forschool.entity.DeviceInfo;
import com.cumt.forschool.vo.DeviceVO;
import com.cumt.forschool.vo.ResultVO;

import java.util.List;

/**
 * @Author: ahui
 * @date: 2022/2/22 - 19:48
 */
public interface DeviceInfoService extends IService<DeviceInfo> {
    ResultVO addDeviceForOrgan(DeviceVO deviceVO);
    List<DeviceInfoDTO> listAllDeviceInfoForOrgan(String organization);
}
