package com.cumt.forschool.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cumt.forschool.entity.ApplyForRent;
import com.cumt.forschool.entity.RoomInfo;
import com.cumt.forschool.entity.RoomUse;
import com.cumt.forschool.mapper.RoomUseMapper;
import com.cumt.forschool.service.ApplyForRentService;
import com.cumt.forschool.service.RoomInfoService;
import com.cumt.forschool.service.RoomUseService;
import com.cumt.forschool.vo.ResultVO;
import com.cumt.forschool.vo.RoomUseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: ahui
 * @date: 2022/2/15 - 18:30
 */
@Service
public class RoomUseServiceImpl extends ServiceImpl<RoomUseMapper, RoomUse> implements RoomUseService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RoomInfoService roomInfoService;


    @Override
    public List<RoomUse> findAllUsingRoomInfo(String roomId) {
        //从redis中根据房间号查询所有的使用房间的信息 如果过期了进行删除
        QueryWrapper<RoomUse> wrapper = new QueryWrapper<>();
        wrapper.eq("room_id",roomId)
                .eq("deleted",0)
                .select("roomuse_id","start_time","end_time");
        List<RoomUse> rooms = baseMapper.selectList(wrapper);
        if (rooms == null){
            return null;
        }
        for (RoomUse room : rooms) {
            LocalDateTime endTime = room.getEndTime();
            if (endTime.isBefore(LocalDateTime.now())){
                room.setDeleted(1);
                baseMapper.updateById(room);
            }
        }
        List<RoomUse> useList = baseMapper.selectList(wrapper);
        return useList;
    }

    @Override
    public int addRoomUse(RoomUse roomUse) {


        if (roomUse.getUsername() == null){
            throw new ApiException("请确定学号");
        }

        LocalDateTime startTime = roomUse.getStartTime();
        LocalDateTime endTime = roomUse.getEndTime();

        List<RoomUse> rooms = findAllUsingRoomInfo(roomUse.getRoomId());
        for (RoomUse roomtemp : rooms) {
            LocalDateTime useStartTime = roomtemp.getStartTime();
            LocalDateTime useEndTime = roomtemp.getEndTime();

            if (startTime.isAfter(useStartTime) && startTime.isBefore(useEndTime)){
                throw new ApiException("租用开始时间冲突");
            }
            if (endTime.isAfter(useStartTime) && endTime.isBefore(useEndTime)){
                throw new ApiException("租用结束时间冲突");
            }
        }


        int insert = baseMapper.insert(roomUse);
        return insert;
    }
}
