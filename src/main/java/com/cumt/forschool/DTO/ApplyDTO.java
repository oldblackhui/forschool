package com.cumt.forschool.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @Author: ahui
 * @date: 2022/6/5 - 15:17
 */
@Data
public class ApplyDTO {
    public String organizationName; // 组织姓名
    public String kind; // 教室还是设备
    public String rentName; // 租借的东西的姓名 某某设备或者某某设备
    public int num; // 数量
    public String applyInfo; // 申请的理由

    public int status; // 申请是否被处理 (同意 拒绝 未处理)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime applyTime; // 开始时间

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime; // 开始时间

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime; // 结束时间



}
