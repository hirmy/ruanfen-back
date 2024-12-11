package com.ruanfen.exception;

import com.ruanfen.model.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExcaptionHandler {

    @ExceptionHandler(Exception.class)
    public Result handleExcaption(Exception e){
        e.printStackTrace();
        return Result.error(StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "操作失败");
    }
}
