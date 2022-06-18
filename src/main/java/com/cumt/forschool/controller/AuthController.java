package com.cumt.forschool.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cumt.forschool.DTO.ApplyDTO;
import com.cumt.forschool.entity.ApplyForRent;
import com.cumt.forschool.entity.MyUserDetail;
import com.cumt.forschool.entity.RoleInfo;
import com.cumt.forschool.vo.*;
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
    @GetMapping("/auth/find/applyinfo")
    public ResultVO findAllApplyInfoByUsername(@RequestParam String username,@RequestParam int current,@RequestParam int size) {
        Page page = new Page(current,size);
        List<ApplyDTO> applyDTOList = applyForRentService.listAllApplyInfoByUsername(username, page);
        return ResultVO.ok(applyDTOList);
    }

    @GetMapping("/manager/find/applyinfo")
    public ResultVO findAllApplyInfoForManager(@RequestParam int current,@RequestParam int size) {
        Page page = new Page(current,size);
        List<ApplyDTO> applyDTOList = applyForRentService.mangerOpsForRentByRole(page);
        return ResultVO.ok(applyDTOList);
    }

    @PostMapping("/refresh") // 刷新token
    public ResultVO refreshToken(HttpServletRequest request){
        return authService.refreshToken(jwTokenUtil.getToken(request));
    }


    @PostMapping("/auth/list/apply")
    public ResultVO listRoomApplyWithStatus(@RequestBody SimpleApplyVO simpleApplyVO){
        List<ApplyForRent> applyForRents = applyForRentService.listApplyForRentByIdAndStatus(simpleApplyVO.getId(), simpleApplyVO.getStatus());
        return ResultVO.ok(applyForRents);
    }

    @PostMapping("/auth/room/change")
    public ResultVO changeRoomStatusForApply(@RequestBody DealApplyVO dealApplyVO){
        return applyForRentService.changeRoomApplyStatus(dealApplyVO);
    }

    @PostMapping("/auth/device/change")
    public ResultVO changeDeviceStatusForApply(@RequestBody DealApplyVO dealApplyVO){
        return applyForRentService.changeDeviceApplyStatus(dealApplyVO);
    }

    @GetMapping("/admin/find/allrole")
    public ResultVO listAllRoleInfo(){
        List<RoleInfo> roleInfos = roleInfoService.listALLRoleInfo();
        return ResultVO.ok(roleInfos);
    }

    @PostMapping("/admin/add/userrole")
    public ResultVO addUserRoleLink(@RequestBody UserRoleIDVO userRoleIDVO){
        ResultVO resultVO = userRoleLinkService.addUserRoleLink(userRoleIDVO.getUsername(), userRoleIDVO.getRoleId());
        return resultVO;
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
