package com.cumt.forschool.vo;

import com.cumt.forschool.enums.ResultVOEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: ahui
 * @date: 2022/2/12 - 22:23
 */
@Slf4j
@Data  // 返回结果统一封装
public class ResultVO<T> {

    private Integer code;

    private String message;

    private T data;

    public ResultVO(){}


    protected static <T> ResultVO<T> build(T data){
        ResultVO<T> result = new ResultVO<T>();
        if (data != null)
            result.setData(data);
        return result;
    }

    public static <T> ResultVO<T> build(T body, ResultVOEnum resultVOEnum) {
        ResultVO<T> result = build(body);
        result.setCode(resultVOEnum.getCode());
        result.setMessage(resultVOEnum.getMessage());
        return result;
    }

    public static <T> ResultVO<T> build(Integer code, String message) {
        ResultVO<T> result = build(null);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /*操作成功*/
    public static <T> ResultVO<T> ok(T data){
        ResultVO<T> result = build(data,ResultVOEnum.SUCCESS);
        return result;
    }

    public static <T> ResultVO<T> ok(){
        return ResultVO.ok(null);
    }

    /*操作失败*/
    public static <T> ResultVO<T> fail(T data){
        ResultVO<T> result = build(data,ResultVOEnum.FAIL);
        return result;
    }

    public static <T> ResultVO<T> fail(){
        return ResultVO.fail(null);
    }


    public ResultVO<T> message(String msg){
        this.setMessage(msg);
        return this;
    }
    public ResultVO<T> code(Integer code) {
        this.setCode(code);
        return this;
    }

    public boolean isOk() {
        if(this.getCode().intValue() == ResultVOEnum.SUCCESS.getCode().intValue()){
            return true;
        }
        return false;
    }
}
