package com.ruanfen.exception;

import com.ruanfen.pojo.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExcaptionHandler {

    @ExceptionHandler(Exception.class)
    public Result handleExcaption(Exception e){
        e.printStackTrace();
        return Result.error(001, StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "操作失败");
    }
}
