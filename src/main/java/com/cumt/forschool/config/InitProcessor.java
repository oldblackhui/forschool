package com.cumt.forschool.config;

import com.cumt.forschool.bo.PermissionBo;
import com.cumt.forschool.entity.RoomInfo;
import com.cumt.forschool.service.PermissionInfoService;
import com.cumt.forschool.service.RoleInfoService;
import com.cumt.forschool.service.RoomInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @Author: ahui
 * @date: 2022/2/13 - 21:04
 * 初始化时候,将权限从数据库取到并放入redis
 */
@Slf4j
@Component
public class InitProcessor {

    @Autowired
    private PermissionInfoService permissionInfoService;

    @Autowired
    private RoomInfoService roomInfoService;

    @Autowired
    private RoleInfoService roleInfoService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostConstruct //被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器执行一次。
    public void init(){
        //将所有可访问的路径初始化
        List<PermissionBo> permissionBoList = permissionInfoService.getAllPermissionInfo();
        for(PermissionBo permissionBo : permissionBoList) {
            redisTemplate.opsForValue().set(permissionBo.getPermissionMethod()+":"+permissionBo.getPermissionUri(),permissionBo);
        }
        // 初始化所有可用的房间
        List<RoomInfo> roomInfoList = roomInfoService.findAllRoomInfo();
        for (RoomInfo roomInfo : roomInfoList) {
            redisTemplate.opsForValue().set("roomId:"+roomInfo.getRoomId(),roomInfo.getRoomId());
        }

    }
}
