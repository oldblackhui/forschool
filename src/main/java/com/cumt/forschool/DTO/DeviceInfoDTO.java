package com.cumt.forschool.DTO;

import lombok.Data;

/**
 * @Author: ahui
 * @date: 2022/2/22 - 20:04
 */
@Data
public class DeviceInfoDTO {

    private String deviceId;
    private String deviceName;
    private Integer total; //总数
    private Integer usingNum;// 租出去的数量
}
