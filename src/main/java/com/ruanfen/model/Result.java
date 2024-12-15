package com.ruanfen.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Result<T> {
    private int code;
    private String message;
    private T data;
    Result(int code){
        this.code = code;
        this.message = null;
        this.data = null;
    }



    public static Result success(){
        return new Result(200, "操作成功", null);
    }

    public static <E> Result<E> success(E data){
        return new Result<>(200, "操作成功", data);
    }

    public static <E> Result<E> success(String message, E data){
        return new Result<>(200, message, data);
    }

    public static Result error(){
        return new Result(400);
    }

    public static Result error(String message){
        return new Result(400, message, null);
    }
}
