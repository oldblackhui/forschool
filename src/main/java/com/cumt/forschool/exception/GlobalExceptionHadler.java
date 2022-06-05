package com.cumt.forschool.exception;


import com.cumt.forschool.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @date 2021/10/10 - 10:22
 */
@Slf4j
@ControllerAdvice  //全局控制异常处理
public class GlobalExceptionHadler {

    //全局异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultVO error(Exception e) {
        log.info("全局异常处理: "+e);
        e.printStackTrace();
        return ResultVO.fail("执行了全局异常处理");
    }

    //自定义异APi常处理
    @ExceptionHandler(ApiException.class)
    @ResponseBody
    public ResultVO error(ApiException e){
        log.info("自定义Api异常处理: "+e);
        e.printStackTrace();
        return ResultVO.build(e.getCode(), e.getMessage());
    }

    //参数校验异常处理
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResultVO<String> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) throws NoSuchFieldException {
        log.error("运行时参数校验异常: ",e);

        BindingResult bindingResult = e.getBindingResult();

        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
        return ResultVO.fail(objectError.getDefaultMessage());
    }

}
