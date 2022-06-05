package com.cumt.forschool.bo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: ahui
 * @date: 2022/2/13 - 21:26
 */
@Data
public class PermissionBo {

    private String id;

    //private String permissionName;

    private String permissionUri;

    private String permissionMethod;

    private String roleName;



}
