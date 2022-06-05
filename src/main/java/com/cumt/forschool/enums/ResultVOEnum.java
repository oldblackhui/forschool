package com.cumt.forschool.enums;

import lombok.Getter;

/**
 * @date 2021/10/10 - 10:27
 */
@Getter
public enum ResultVOEnum {

    SUCCESS(200,"操作成功"),
    LOGIN_REQUIRED(203,"请先登录"),
    FAIL(400,"操作失败"),
    AUTH_FAIL(401,"认证失败"),
    PERMISSION_FAIL(403,"权限不支持此操作"),
    USERNAME_WRONG(1000,"用户名错误"),
    PASSWORD_WRONG(1001,"密码错误"),
    REGISTERED(1002,"用户已注册"),
    PARAMETER_ERROR(1003,"参数错误,请检查"),
    NOT_FOUND(10004,"资源不存在或未找到"),
    REPEAT_FORBIDDEN(1005,"禁止重复操作"),
    USER_NOT_EXISTS(1006,"用户不存在"),
    LOGIN_FAIL(500,"登陆失败")
    ;

    private Integer code;
    private String message;

    private ResultVOEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
