package com.cumt.forschool.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cumt.forschool.DTO.OrganizationDTO;
import com.cumt.forschool.entity.OrganizationInfo;
import com.cumt.forschool.mapper.OrganizationInfoMapper;
import com.cumt.forschool.service.OrganizationInfoService;
import com.cumt.forschool.vo.OrganizationVO;
import com.cumt.forschool.vo.ResultVO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ahui
 * @date: 2022/2/14 - 23:27
 */
@Service
public class OrganizationInfoServiceImpl extends ServiceImpl<OrganizationInfoMapper, OrganizationInfo> implements OrganizationInfoService {


    @Override
    public ResultVO selectAllOrganizations() {
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
        return ResultVO.ok(list);
    }

    @Override
    public ResultVO addOrganization(OrganizationVO organizationVO, String managerId) {
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
        if (insert == 1){
            return ResultVO.ok(name+"创建"+organizationInfo.getOrganizationName()+"成功!");
        }
        return ResultVO.fail("创建失败,请检查参数或者权限");
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
