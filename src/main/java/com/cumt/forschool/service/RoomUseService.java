package com.cumt.forschool.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cumt.forschool.entity.RoomUse;
import com.cumt.forschool.vo.ResultVO;
import com.cumt.forschool.vo.RoomUseVO;

import java.util.List;

/**
 * @Author: ahui
 * @date: 2022/2/15 - 18:29
 */
public interface RoomUseService extends IService<RoomUse> {


    //查询所有的正在使用的房间信息 并且每次查询检查是否过期 过期了就进行伪删除
    List<RoomUse> findAllUsingRoomInfo(String roomId);

    int addRoomUse(RoomUse roomUse);
}
