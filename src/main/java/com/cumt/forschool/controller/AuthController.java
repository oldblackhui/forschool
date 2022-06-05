package com.cumt.forschool.controller;

import com.cumt.forschool.entity.ApplyForRent;
import com.cumt.forschool.entity.MyUserDetail;
import com.cumt.forschool.vo.DealApplyVO;
import com.cumt.forschool.vo.SimpleApplyVO;
import com.cumt.forschool.vo.LoginVO;
import com.cumt.forschool.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: ahui
 * @date: 2022/2/13 - 17:37
 */
@Slf4j
@RestController
public class AuthController extends BaseController{

    @PostMapping("/api/auth/login")
    public ResultVO login(@RequestBody LoginVO loginVO){

        ResultVO login = authService.login(loginVO.getUsername(), loginVO.getPassword());
        return login;
    }

    @PostMapping("/api/auth/logout")
    public ResultVO logout(){
        return authService.logout();
    }

    @GetMapping("/auth/find/userinfo")
    public ResultVO getUserInfo(@RequestParam String username){
        return userInfoService.getUserInfo(username);
    }


    @PostMapping("/refresh") // 刷新token
    public ResultVO refreshToken(HttpServletRequest request){
        return authService.refreshToken(jwTokenUtil.getToken(request));
    }


    @PostMapping("/auth/list/apply")
    public List<ApplyForRent> listRoomApplyWithStatus(@RequestBody SimpleApplyVO simpleApplyVO){
        return applyForRentService.listApplyForRentByIdAndStatus(simpleApplyVO.getId(), simpleApplyVO.getStatus());
    }

    @PostMapping("/auth/room/change")
    public ResultVO changeRoomStatusForApply(@RequestBody DealApplyVO dealApplyVO){
        return applyForRentService.changeRoomApplyStatus(dealApplyVO);
    }

    @PostMapping("/auth/device/change")
    public ResultVO changeDeviceStatusForApply(@RequestBody DealApplyVO dealApplyVO){
        return applyForRentService.changeDeviceApplyStatus(dealApplyVO);
    }


    @GetMapping("/api/anon")
    public ResultVO test01() {
        return ResultVO.ok("匿名访问成功");
    }

    @GetMapping("/api/user")
    public ResultVO test02() {
        return ResultVO.ok("USER用户访问成功");
    }

    @GetMapping("/api/admin")
    public ResultVO test03() {
        List<String> roles = ((MyUserDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getRoles();
        log.info("权限为: "+roles);
        System.out.println((MyUserDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return ResultVO.ok("管理员用户访问成功");
    }
}
