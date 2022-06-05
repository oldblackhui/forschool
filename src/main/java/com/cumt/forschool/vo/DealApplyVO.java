package com.cumt.forschool.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @Author: ahui
 * @date: 2022/6/5 - 11:03
 */
@Data
public class DealApplyVO {
    //可能是组织id 也可能是某个房间的id
    private String applyId;
    private int status;
    private String dealInfo;
}
