package com.cumt.forschool.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cumt.forschool.DTO.OrganizationDTO;
import com.cumt.forschool.entity.OrganizationInfo;
import com.cumt.forschool.vo.OrganizationVO;
import com.cumt.forschool.vo.ResultVO;

import java.util.List;

/**
 * @Author: ahui
 * @date: 2022/2/14 - 23:26
 */
public interface OrganizationInfoService extends IService<OrganizationInfo> {


    ResultVO selectAllOrganizations();

    //添加一个组织
    ResultVO addOrganization(OrganizationVO organizationVO, String managerId);

    //查询某个组织
    OrganizationInfo selectOrganizationById(String organizationId);
}
