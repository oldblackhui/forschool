package com.cumt.forschool.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: ahui
 * @date: 2022/2/12 - 18:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessToken {
    private String loginAccount;
    private String token;
    private Date expirationTime;
}
