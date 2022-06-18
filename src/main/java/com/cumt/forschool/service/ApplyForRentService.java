package com.cumt.forschool.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cumt.forschool.DTO.ApplyDTO;
import com.cumt.forschool.entity.ApplyForRent;
import com.cumt.forschool.vo.ApplyUseVO;
import com.cumt.forschool.vo.DealApplyVO;
import com.cumt.forschool.vo.ResultVO;

import java.util.List;

/**
 * @Author: ahui
 * @date: 2022/2/22 - 19:48
 */
public interface ApplyForRentService extends IService<ApplyForRent> {

    //根据拥有的权限查出所有未处理的操作的申请信息(管理员用)
    public List<ApplyDTO> mangerOpsForRentByRole(Page page);

    //通过学号查看所有的申请信息
    public List<ApplyDTO> listAllApplyInfoByUsername(String username, Page page);


    /* 教室 设备 器材 公用*/
    // 提交一个申请记录
    public int addOneApplyRecord(ApplyForRent applyForRent);

    // 根据组织id 和 指定状态 查询申请
    public List<ApplyForRent> listApplyForRentByIdAndStatus(String organizationId, int status);

    /* 教室 */
    // 申请一个房间
    public ResultVO applyForRoomUse(ApplyUseVO applyUseVO);

    //根据申请的id 和 状态 对房间同意申请或者拒绝申请
    public ResultVO changeRoomApplyStatus(DealApplyVO dealApplyVO);


    /*设备*/

    // 申请借用设备
    public ResultVO applyForDeviceUse(ApplyUseVO applyUseVO);

    // 根据申请id 和 状态 对设备借用进行同意或者拒绝
    public ResultVO changeDeviceApplyStatus(DealApplyVO dealApplyVO);

}
