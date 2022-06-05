package com.cumt.forschool.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cumt.forschool.DTO.DeviceInfoDTO;
import com.cumt.forschool.entity.DeviceInfo;
import com.cumt.forschool.entity.DeviceUse;
import com.cumt.forschool.mapper.DeviceInfoMapper;
import com.cumt.forschool.mapper.DeviceUseMapper;
import com.cumt.forschool.service.DeviceInfoService;
import com.cumt.forschool.service.DeviceUseService;
import com.cumt.forschool.vo.DeviceVO;
import com.cumt.forschool.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ahui
 * @date: 2022/2/22 - 19:48
 */
@Service
public class DeviceInfoServiceImpl extends ServiceImpl<DeviceInfoMapper, DeviceInfo> implements DeviceInfoService {
    @Autowired
    DeviceUseService deviceUseService;
    @Override
    public ResultVO addDeviceForOrgan(DeviceVO deviceVO) {
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setDevicename(deviceVO.getDevicename());
        deviceInfo.setOrganizationId(deviceVO.getOrganizationId());
        deviceInfo.setNum(deviceVO.getNum());
        deviceInfo.setInfo(deviceVO.getInfo());
        int i = baseMapper.insert(deviceInfo);
        if (i == 0) return ResultVO.fail("插入失败,请检查参数");
        return ResultVO.ok("新增设备成功");
    }

    @Override
    public List<DeviceInfoDTO> listAllDeviceInfoForOrgan(String organizationId) {
        QueryWrapper<DeviceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("organization_id",organizationId)
                    .eq("deleted",0)
                    .select("device_id","device_name","num","info");
        List<DeviceInfo> devices = baseMapper.selectList(queryWrapper);
        if (devices.isEmpty() || devices == null){
            throw new ApiException("请输入正确的组织id");
        }
        List<DeviceInfoDTO> devicesInfo = new ArrayList<>();
        for (DeviceInfo device : devices) {
            DeviceInfoDTO deviceInfoDTO = new DeviceInfoDTO();
            int usingCount = deviceUseService.findUsingDeviceNum(device.getDeviceId());
            deviceInfoDTO.setDeviceId(device.getDeviceId());
            deviceInfoDTO.setDeviceName(device.getDevicename());
            deviceInfoDTO.setTotal(device.getNum());
            deviceInfoDTO.setUsingNum(usingCount);
            devicesInfo.add(deviceInfoDTO);
        }

        return devicesInfo;
    }
}
