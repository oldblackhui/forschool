package com.cumt.forschool.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cumt.forschool.entity.RoleInfo;
import com.cumt.forschool.entity.RoomInfo;
import com.cumt.forschool.vo.ResultVO;

import java.util.List;

/**
 * @Author: ahui
 * @date: 2022/2/15 - 19:30
 */
public interface RoomInfoService extends IService<RoomInfo> {

    ResultVO addRoomWithOrganization(RoomInfo roomInfo);

    ResultVO findAllRoomByOrganId(String organId);

    // 查询出所有的房间信息 初始化使用
    List<RoomInfo> findAllRoomInfo();
}
