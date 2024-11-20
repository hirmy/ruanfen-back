package com.ruanfen.pojo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Result<T> {
    private int code;
    private String message;
    private T data;
    Result(int code){
        this.code = code;
        this.message = null;
        this.data = null;
    }



    public static Result success(int code){
        return new Result(code, "操作成功", null);
    }

    public static <E> Result<E> success(int code, E data){
        return new Result<>(code, "操作成功", data);
    }

    public static <E> Result<E> success(int code, String message, E data){
        return new Result<>(code, message, data);
    }

    public static Result error(int code){
        return new Result(code);
    }

    public static Result error(int code, String message){
        return new Result(code, message, null);
    }
}
