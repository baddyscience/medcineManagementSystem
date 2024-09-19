package com.medicine.springboot.exception;

import com.medicine.springboot.common.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class globalException {

    @ExceptionHandler(serviceException.class)
    @ResponseBody
    public Result serviceException(serviceException e) {
        return Result.error(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result GlobalException(Exception e) {
        return Result.error("500", "系统错误" + e.getMessage());
    }

}
