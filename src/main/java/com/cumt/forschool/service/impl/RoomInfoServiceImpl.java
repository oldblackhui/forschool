package com.cumt.forschool.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cumt.forschool.entity.RoomInfo;
import com.cumt.forschool.mapper.RoomInfoMapper;
import com.cumt.forschool.service.OrganizationInfoService;
import com.cumt.forschool.service.RoomInfoService;
import com.cumt.forschool.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @Author: ahui
 * @date: 2022/2/15 - 19:31
 */
@Slf4j
@Service
public class RoomInfoServiceImpl extends ServiceImpl<RoomInfoMapper, RoomInfo> implements RoomInfoService {

    @Autowired
    OrganizationInfoService organizationInfoService;

    @Override
    public ResultVO addRoomWithOrganization(RoomInfo roomInfo) {
        if (roomInfo.getOrganizationId() == null ||
                organizationInfoService.selectOrganizationById(roomInfo.getOrganizationId()) == null){
            throw new ApiException("请检查组织id");
        }
        int insert = baseMapper.insert(roomInfo);
        log.info("生成的主键为: "+ roomInfo.getRoomId());
        if (insert == 0){
            ResultVO.fail("新增房间失败");
        }

        return ResultVO.ok("添加房间成功");
    }

    @Override
    public ResultVO findAllRoomByOrganId(String organId) {
        QueryWrapper<RoomInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("organization_id",organId).eq("deleted",0)
                        .select("room_name","room_id","leader","introduction");
        List<RoomInfo> rooms = baseMapper.selectList(wrapper);
        if (rooms == null){
            return ResultVO.fail("没有此组织管理的房间");
        }
        return ResultVO.ok(rooms);
    }

    @Override
    public List<RoomInfo> findAllRoomInfo() {
        QueryWrapper<RoomInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted",0)
                        .select("room_id","room_name","organization_id");
        List<RoomInfo> roomInfos = baseMapper.selectList(wrapper);
        return roomInfos;
    }
}
