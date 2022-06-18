package com.cumt.forschool.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cumt.forschool.DTO.OrganizationDTO;
import com.cumt.forschool.entity.OrganizationInfo;
import com.cumt.forschool.exception.ApiException;
import com.cumt.forschool.mapper.OrganizationInfoMapper;
import com.cumt.forschool.service.OrganizationInfoService;
import com.cumt.forschool.service.RoleInfoService;
import com.cumt.forschool.vo.OrganizationVO;
import com.cumt.forschool.vo.ResultVO;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ahui
 * @date: 2022/2/14 - 23:27
 */
@Service
@Slf4j
public class OrganizationInfoServiceImpl extends ServiceImpl<OrganizationInfoMapper, OrganizationInfo> implements OrganizationInfoService {


    @Autowired
    RoleInfoService roleInfoService;


    @Override
    public List<OrganizationDTO> selectAllOrganizations() {
        QueryWrapper<OrganizationInfo> wrapper = new QueryWrapper<>();
        wrapper.select("organization_id","organization_name","leader","phone","introduction","location")
                .eq("deleted",0);
        List<OrganizationInfo> organizationInfos = baseMapper.selectList(wrapper);
        List<OrganizationDTO> list = new ArrayList<>();
        for (OrganizationInfo organizationInfo : organizationInfos) {
            OrganizationDTO organizationDTO = new OrganizationDTO();
            organizationDTO.setOrganizationId(organizationInfo.getOrganizationId());
            organizationDTO.setOrganizationName(organizationInfo.getOrganizationName());
            organizationDTO.setLeader(organizationInfo.getLeader());
            organizationDTO.setPhone(organizationInfo.getPhone());
            organizationDTO.setLocation(organizationInfo.getLocation());
            organizationDTO.setIntroduction(organizationInfo.getIntroduction());
            list.add(organizationDTO);
        }
        return list;
    }

    @Override
    public List<OrganizationInfo> selectAllOrganizationInfo() {
        QueryWrapper<OrganizationInfo> wrapper = new QueryWrapper<>();
        wrapper.select("organization_id","organization_name","leader","phone","introduction","location","role_name")
                .eq("deleted",0);
        List<OrganizationInfo> organizationInfos = baseMapper.selectList(wrapper);
        log.info("组织全部信息"+organizationInfos);
        return organizationInfos;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO addOrganization(OrganizationVO organizationVO, String managerId) {

        if (StringUtil.isNullOrEmpty(organizationVO.getRoleName())){
            return ResultVO.fail("请输入组织的role");
        }

        int i = roleInfoService.addRoleAboutOrgan(organizationVO.getRoleName());
        if ( i == 0){
            throw new ApiException("role创建失败 请检查参数");
        }

        OrganizationInfo organizationInfo = new OrganizationInfo();
        organizationInfo.setManagerId(managerId);
        organizationInfo.setOrganizationName(organizationVO.getOrganizationName());
        organizationInfo.setLeader(organizationVO.getLeader());
        organizationInfo.setPhone(organizationVO.getPhone());
        organizationInfo.setIntroduction(organizationVO.getIntroduction());
        organizationInfo.setLocation(organizationVO.getLocation());



        int insert = baseMapper.insert(organizationInfo);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        if (insert == 0){
            throw new ApiException("创建组织失败");
        }
        return ResultVO.ok(name+"创建"+organizationInfo.getOrganizationName()+"成功!");
    }


    @Override
    public OrganizationInfo selectOrganizationById(String organizationId) {
        OrganizationInfo organizationInfo = baseMapper.selectById(organizationId);
        if (organizationInfo == null){
            return null;
        }
        return organizationInfo;
    }
}
