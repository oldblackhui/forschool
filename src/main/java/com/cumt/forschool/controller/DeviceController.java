package com.cumt.forschool.controller;

import com.cumt.forschool.DTO.DeviceInfoDTO;
import com.cumt.forschool.vo.ApplyUseVO;
import com.cumt.forschool.vo.DeviceVO;
import com.cumt.forschool.vo.ResultVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: ahui
 * @date: 2022/2/22 - 20:27
 */
@RestController
public class DeviceController extends BaseController{

    @PostMapping("/device/add")
    public ResultVO addDeviceForOrgan(@RequestBody DeviceVO deviceVO){
        ResultVO resultVO = deviceInfoService.addDeviceForOrgan(deviceVO);
        return resultVO;
    }

    @GetMapping("/device/find/all")
    public ResultVO findOrganAllDevices(@RequestParam String organId){
        List<DeviceInfoDTO> devices = deviceInfoService.listAllDeviceInfoForOrgan(organId);
        if(devices == null){
            return ResultVO.fail("该组织没有设备");
        }
        return ResultVO.ok(devices);
    }
    @PostMapping("/device/use/apply") // 申请房间使用
    public ResultVO addRoomUseInfo(@RequestBody ApplyUseVO applyUseVO){
        return applyForRentService.applyForDeviceUse(applyUseVO);
    }

}
