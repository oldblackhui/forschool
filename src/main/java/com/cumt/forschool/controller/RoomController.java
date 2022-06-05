package com.cumt.forschool.controller;

import com.cumt.forschool.entity.ApplyForRent;
import com.cumt.forschool.entity.RoomInfo;
import com.cumt.forschool.entity.RoomUse;
import com.cumt.forschool.service.RoomUseService;
import com.cumt.forschool.vo.ApplyUseVO;
import com.cumt.forschool.vo.ResultVO;
import com.cumt.forschool.vo.RoomUseVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: ahui
 * @date: 2022/2/20 - 15:51
 */
@RestController
public class RoomController extends BaseController{

    @GetMapping("/room/find/all")
    public ResultVO findAllRoomByOrganId(@RequestParam String organId){
        return roomInfoService.findAllRoomByOrganId(organId);
    }

    @PostMapping("/room/use/apply") // 申请房间使用
    public ResultVO addRoomUseInfo(@RequestBody ApplyUseVO applyUseVO){
        return applyForRentService.applyForRoomUse(applyUseVO);
    }
    @GetMapping("room/find/using")
    public ResultVO findRoomUsingInfo(@RequestParam String roomId){
        List<RoomUse> allUsingRoomInfo = roomUseService.findAllUsingRoomInfo(roomId);
        return ResultVO.ok(allUsingRoomInfo);
    }

}
