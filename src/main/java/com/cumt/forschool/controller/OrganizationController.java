package com.cumt.forschool.controller;

import com.cumt.forschool.entity.RoomInfo;
import com.cumt.forschool.vo.OrganizationVO;
import com.cumt.forschool.vo.ResultVO;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: ahui
 * @date: 2022/2/14 - 23:38
 */
@RestController
public class OrganizationController extends BaseController{

    @PostMapping("/organ/add")
    public ResultVO addOrganization(@RequestBody OrganizationVO organizationVO){
        String manager_id = getUsernameFromAuth();
        return organizationInfoService.addOrganization(organizationVO,manager_id);
    }

    @PostMapping("/room/add")
    public ResultVO addRoom(@RequestBody RoomInfo roomInfo){
        return roomInfoService.addRoomWithOrganization(roomInfo);
    }

    @GetMapping("/organ/find/all")
    public ResultVO listAllOrganizations(){

        return ResultVO.ok(organizationInfoService.selectAllOrganizations());
    }


    @PutMapping("/organ/update")
    public ResultVO updateOrganization(@RequestBody OrganizationVO organizationVO){
        return ResultVO.ok();
    }

}
