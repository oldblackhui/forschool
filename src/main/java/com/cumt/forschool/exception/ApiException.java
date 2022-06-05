package com.cumt.forschool.exception;

import com.cumt.forschool.enums.ResultVOEnum;
import lombok.Data;

/**
 * @date 2021/10/10 - 10:15
 */
@Data
public class ApiException extends RuntimeException{
    private Integer code;

    public ApiException(Integer code,String message){
        super(message);
        this.code = code;
    }
    public ApiException(String message){
        super(message);
    }

    public ApiException(int code){
        this.code = code;
    }
    public ApiException(ResultVOEnum resultVOEnum){
        super(resultVOEnum.getMessage());
        this.code = resultVOEnum.getCode();
    }

    @Override
    public String toString() {
        return "ApiException:"+"code = "+code+" ,message = "+this.getMessage();
    }
}
