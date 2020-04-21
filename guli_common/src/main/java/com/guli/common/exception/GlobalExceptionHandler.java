package com.guli.common.exception;

import com.guli.common.entity.Result;
import com.guli.common.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public Result errorGuliException(GuliException e){
        e.printStackTrace();
        //1、把e异常的栈中的信息写出到流中；2、然后log.error输出
        log.error(ExceptionUtil.getMessage(e));
        return Result.error().message(e.getMessage()).code(e.getCode());
    }


    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result errorArithmeticException(ArithmeticException e){
        e.printStackTrace();
        log.error(ExceptionUtil.getMessage(e));
        return Result.error().message("你憨啊、除数不能为0");
    }

    // ExceptionHandler ： 是来捕获什么异常类型； Exception 所有异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        log.error(ExceptionUtil.getMessage(e));
        return Result.error();
    }

}
