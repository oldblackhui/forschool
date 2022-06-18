package com.cumt.forschool.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cumt.forschool.DTO.ApplyDTO;
import com.cumt.forschool.entity.*;
import com.cumt.forschool.mapper.ApplyForRentMapper;
import com.cumt.forschool.service.*;
import com.cumt.forschool.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @Author: ahui
 * @date: 2022/2/22 - 19:48
 */
@Service
@Slf4j
public class ApplyForRentServiceImpl extends ServiceImpl<ApplyForRentMapper, ApplyForRent> implements ApplyForRentService {

    @Autowired
    private RoomUseService roomUseService;

    @Autowired
    private RoomInfoService roomInfoService;

    @Autowired
    private DeviceInfoService deviceInfoService;

    @Autowired
    private DeviceUseService deviceUseService;

    @Autowired
    private OrganizationInfoService organizationInfoService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<ApplyDTO> mangerOpsForRentByRole(Page page) {
        Set members = redisTemplate.opsForSet().members(SecurityContextHolder.getContext().getAuthentication().getName() + ":role");
        List<String> roles = new ArrayList<>();
        for (Object s : members) {
            roles.add(s.toString());
        }
        List<OrganizationInfo> organList = organizationInfoService.selectAllOrganizationInfo();
        log.info("organList size "+organList.size());
        log.info(" ==== "+organList.get(0));
        List<String> organIds = new ArrayList<>();
        HashMap<String,String> organIdAndName = new HashMap<>();
        for (OrganizationInfo info : organList) {
            log.info("organinfo:"+info.getRoleName());
            log.info(""+roles.contains(info.getRoleName()));
            if (roles.contains(info.getRoleName())){
                organIds.add(info.getOrganizationId());
                organIdAndName.put(info.getOrganizationId(),info.getOrganizationName());
            }
        }
        log.info("组织id"+organIds);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.select("apply_id","organization_id","username","using_id","using_kind","status","num","start_time","end_time","apply_info","apply_time");
        wrapper.eq("deleted",0);
        wrapper.in("organization_id",organIds);

        wrapper.orderByDesc("status","apply_time");


        IPage<ApplyForRent> iPage = baseMapper.selectPage(page, wrapper);
        List<ApplyDTO> applyDTOList = new ArrayList<>();
        for (ApplyForRent record : iPage.getRecords()) {
            ApplyDTO applyDTO = new ApplyDTO();
            applyDTO.setOrganizationName(organIdAndName.get(record.getOrganizationId()));
            if (record.getUsingKind() == 1){
                applyDTO.setKind("教室");
                applyDTO.setRentName(roomInfoService.getById(record.getUsingId()).getRoomName());
            }
            if (record.getUsingKind() == 2){
                applyDTO.setKind("设备");
                applyDTO.setRentName(deviceInfoService.getById(record.getUsingId()).getDevicename());
            }
            applyDTO.setNum(record.getNum());
            applyDTO.setApplyInfo(record.getApplyInfo());
            applyDTO.setApplyTime(record.getApplyTime());
            applyDTO.setStatus(record.getStatus());
            applyDTO.setApplyInfo(record.getApplyInfo());
            applyDTO.setStartTime(record.getStartTime());
            applyDTO.setEndTime(record.getEndTime());
            applyDTOList.add(applyDTO);
        }
        return applyDTOList;
    }


    @Override
    public List<ApplyDTO> listAllApplyInfoByUsername(String username, Page page) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("username",username);
        wrapper.eq("deleted",0);

        wrapper.select("organization_id","using_id","using_kind","status","num","start_time","end_time","apply_info","apply_time");
        wrapper.orderByDesc("apply_time");
        IPage<ApplyForRent> iPage = baseMapper.selectPage(page, wrapper);
        List<ApplyDTO> applyDTOList = new ArrayList<>();
        for (ApplyForRent record : iPage.getRecords()) {
            ApplyDTO applyDTO = new ApplyDTO();
            applyDTO.setOrganizationName(organizationInfoService.selectOrganizationById(record.getOrganizationId()).getOrganizationName());
            if(record.getUsingKind() == 1) { // 教室
                applyDTO.setKind("教室");
                applyDTO.setRentName(roomInfoService.getById(record.getUsingId()).getRoomName());
            }else if(record.getUsingKind() == 2) { // 设备
                applyDTO.setKind("设备");
                applyDTO.setRentName(deviceInfoService.getById(record.getUsingId()).getDevicename());
            }
            applyDTO.setNum(record.getNum());
            applyDTO.setStatus(record.getStatus());
            applyDTO.setApplyInfo(record.getApplyInfo());
            applyDTO.setApplyTime(record.getApplyTime());
            applyDTO.setStartTime(record.getStartTime());
            applyDTO.setEndTime(record.getEndTime());
            applyDTOList.add(applyDTO);
        }


        return applyDTOList;
    }

    @Override
    public int addOneApplyRecord(ApplyForRent applyForRent) {
        int insert = baseMapper.insert(applyForRent);
        return insert;
    }

    @Override
    public List<ApplyForRent> listApplyForRentByIdAndStatus(String organizationId, int status) {
        QueryWrapper<ApplyForRent> wrapper = new QueryWrapper<>();
        wrapper.eq("organization_id",organizationId)
                .eq("status",status);
        List<ApplyForRent> applyForRents = baseMapper.selectList(wrapper);
        return applyForRents;
    }


    @Override
    public ResultVO applyForRoomUse(ApplyUseVO applyUseVO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if (username == null){
            throw new ApiException("您还未登录,请先登录");
        }
        RoomInfo byId = roomInfoService.getById(applyUseVO.getUsingId());
        if (byId == null || byId.getDeleted() == 1){
            return ResultVO.fail("请选择正确的房间id");
        }
        ApplyForRent applyForRent = new ApplyForRent();
        applyForRent.setStartTime(applyUseVO.getStartTime());
        applyForRent.setEndTime(applyUseVO.getEndTime());
        applyForRent.setUsername(username);
        if (redisTemplate.opsForValue().get("roomId:"+applyUseVO.getUsingId()) == null){
            return ResultVO.fail("没有此房间,请选择正确的房间");
        }
        LocalDateTime startTime = applyUseVO.getStartTime();
        LocalDateTime endTime = applyUseVO.getEndTime();
        if (startTime.isBefore(LocalDateTime.now())){
            return ResultVO.fail("租用时间应晚于当前时间");
        }
        log.info("start: "+startTime+"  "+endTime);
        List<RoomUse> rooms = roomUseService.findAllUsingRoomInfo(applyUseVO.getUsingId());
        for (RoomUse roomUse : rooms) {
            LocalDateTime useStartTime = roomUse.getStartTime();
            LocalDateTime useEndTime = roomUse.getEndTime();
            log.info("use: "+useStartTime+"  "+useEndTime);
            if (startTime.isAfter(useStartTime) && startTime.isBefore(useEndTime) && !startTime.isEqual(useStartTime) ){
                return ResultVO.fail("租用开始时间冲突");
            }
            if (endTime.isAfter(useStartTime) && endTime.isBefore(useEndTime) && !endTime.isEqual(useEndTime)){
                return ResultVO.fail("租用结束时间冲突");
            }
        }
        // 检查无误后 提交一条申请等待 处理
        applyForRent.setOrganizationId(applyUseVO.getOrganizationId());
        applyForRent.setUsingId(applyUseVO.getUsingId());
        applyForRent.setUsingKind(1);
        applyForRent.setApplyInfo(applyUseVO.getApplyInfo());
        applyForRent.setNum(1);
        applyForRent.setStatus(2);
        applyForRent.setApplyTime(LocalDateTime.now());
        applyForRent.setDealTime(LocalDateTime.now());
        int i = addOneApplyRecord(applyForRent);

        if (i == 0){
            return ResultVO.fail("提交房间申请失败");
        }
        return ResultVO.ok("房间申请已经提交, 请等待管理员处理");
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO changeRoomApplyStatus(DealApplyVO dealApplyVO) {
        ApplyForRent apply = baseMapper.selectById(dealApplyVO.getApplyId());
        if (apply == null ){
            throw new ApiException("没有此申请id 请确定id");
        }
        if (apply.getStatus() != 2 || apply.getUsingKind() != 1) {
            return ResultVO.fail("此申请已经处理 或请输入正确的申请id");
        }
        // 看看当前时间段 有没有人使用
        LocalDateTime startTime = apply.getStartTime();
        LocalDateTime endTime = apply.getEndTime();
        List<RoomUse> roomUseList = roomUseService.findAllUsingRoomInfo(apply.getUsingId());
        for (RoomUse roomUse : roomUseList) {
            LocalDateTime useStartTime = roomUse.getStartTime();
            LocalDateTime useEndTime = roomUse.getEndTime();
            if ((startTime.isAfter(useStartTime) && startTime.isBefore(useEndTime) && !startTime.isEqual(useStartTime) && !endTime.isEqual(useEndTime)) ||
                    (endTime.isAfter(useStartTime) && endTime.isBefore(useEndTime)) ) {
                apply.setStatus(0); // 有时间冲突 拒绝借用

                baseMapper.updateById(apply);
                return ResultVO.fail("此时间段内房间已有人使用, 请重新申请");
            }
        }
        RoomUse roomUse = new RoomUse();
        roomUse.setUsername(apply.getUsername());
        roomUse.setRoomId(apply.getUsingId());
        roomUse.setInfo(apply.getApplyInfo());
        if (dealApplyVO.getStatus() == 1){
            apply.setStatus(dealApplyVO.getStatus());// 同意租借房间
            apply.setDealTime(LocalDateTime.now());
            apply.setDealInfo(dealApplyVO.getDealInfo());
            int i = baseMapper.updateById(apply);

            roomUse.setStartTime(apply.getStartTime());
            roomUse.setEndTime(apply.getEndTime());
            int i1 = roomUseService.addRoomUse(roomUse);
            if (i1 == 0 || i == 0){
                throw new ApiException("更新错误 或 插入房间使用错误");
            }
            return ResultVO.ok("已同意房间申请");
        } else if (dealApplyVO.getStatus() == 0){
            apply.setDealTime(LocalDateTime.now());
            apply.setStatus(dealApplyVO.getStatus());
            apply.setDealInfo(dealApplyVO.getDealInfo());
            int i = baseMapper.updateById(apply);
            if( i == 1){
                return ResultVO.ok("已经成功拒绝");
            }else {
                return ResultVO.fail("拒绝失败, 请联系管理员查看");
            }
        }
        return ResultVO.fail("请选择正确的状态码");
    }

    @Override
    public ResultVO applyForDeviceUse(ApplyUseVO applyUseVO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if (username == null){
            throw new ApiException("您还未登录,请先登录");
        }

        String deviceId = applyUseVO.getUsingId();
        DeviceInfo byId = deviceInfoService.getById(deviceId);
        if (byId == null || byId.getDeleted() == 1) {
            return ResultVO.fail("请输入正确的设备id");
        }
        ApplyForRent applyForRent = new ApplyForRent();
        applyForRent.setUsername(username);
        int totalNum = deviceInfoService.getById(deviceId).getNum();
        int usingNum = deviceUseService.findUsingDeviceNum(deviceId);
        if (usingNum + applyUseVO.getNum() > totalNum){
            return ResultVO.fail("租借数量超出剩余数量");
        }
        applyForRent.setOrganizationId(applyUseVO.getOrganizationId());
        applyForRent.setUsingId(deviceId);
        applyForRent.setUsingKind(2);
        applyForRent.setApplyInfo(applyUseVO.getApplyInfo());
        applyForRent.setNum(applyUseVO.getNum());
        applyForRent.setStatus(2);
        applyForRent.setStartTime(applyUseVO.getStartTime());
        applyForRent.setEndTime(applyUseVO.getEndTime());
        applyForRent.setApplyTime(LocalDateTime.now());
        applyForRent.setDealTime(LocalDateTime.now());
        int i = addOneApplyRecord(applyForRent);
        if (i == 0){
            return ResultVO.fail("提交申请失败 请重试");
        }
        return ResultVO.ok("提交申请成功 请等待管理员审批");
    }

    @Override
    @Transactional
    public ResultVO changeDeviceApplyStatus(DealApplyVO dealApplyVO) {
        ApplyForRent applyForRent = baseMapper.selectById(dealApplyVO.getApplyId());
        if (applyForRent == null || applyForRent.getUsingKind() != 2){
            throw new ApiException("请输入正确的id");
        }
        if (applyForRent.getStatus() != 2){
            return ResultVO.fail("此id已经处理");
        }
        applyForRent.setStatus(dealApplyVO.getStatus());
        if (dealApplyVO.getStatus() == 0){
            applyForRent.setDealTime(LocalDateTime.now());
            applyForRent.setDealInfo(dealApplyVO.getDealInfo());
            int i = baseMapper.updateById(applyForRent);
            if (i == 0) {
                throw new ApiException("更新失败");
            }
        } else if (dealApplyVO.getStatus() == 1){
            applyForRent.setDealTime(LocalDateTime.now());
            applyForRent.setDealInfo(dealApplyVO.getDealInfo());
            int i = baseMapper.updateById(applyForRent);
            DeviceUse deviceUse = new DeviceUse();
            deviceUse.setUsername(applyForRent.getUsername());
            deviceUse.setDeviceId(applyForRent.getUsingId());
            deviceUse.setInfo(applyForRent.getApplyInfo());
            deviceUse.setStartTime(applyForRent.getStartTime());
            deviceUse.setEndTime(applyForRent.getEndTime());
            deviceUse.setNum(applyForRent.getNum());
            int i1 = deviceUseService.addRoomUse(deviceUse);
            if (i == 0 || i1 == 0){
                throw new ApiException("更新记录或者插入设备借用失败");
            }
        } else {
            return ResultVO.fail("处理失败");
        }

        if (dealApplyVO.getStatus() == 0){
            return ResultVO.ok("已拒绝设备申请");
        } else {
            return ResultVO.ok("已同意设备申请");
        }
    }


}
