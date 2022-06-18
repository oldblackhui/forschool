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


    //对外展示的信息
    List<OrganizationDTO> selectAllOrganizations();

    //查询数据库中所有组织信息 程序内部使用
    List<OrganizationInfo> selectAllOrganizationInfo();

    //添加一个组织
    ResultVO addOrganization(OrganizationVO organizationVO, String managerId);

    //查询某个组织
    OrganizationInfo selectOrganizationById(String organizationId);
}
