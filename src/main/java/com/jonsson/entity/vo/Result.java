package com.jonsson.entity.vo;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;

    public static Result<Object> success(Object object) {
        Result<Object> result = new Result<>();
        result.setCode(1);
        result.setMsg("成功");
        result.setData(object);
        return result;
    }

    public static Result<Object> success() {
        return success(null);
    }

    public static Result<Object> fail(String msg) {
        Result<Object> result = new Result<>();
        result.setCode(0);
        result.setMsg(msg);
        return result;
    }

    public static Result<Object> fail(Integer code, String msg, Object object) {
        Result<Object> result = new Result<>();
        result.setCode(0);
        result.setMsg(msg);
        result.setData(object);
        return result;
    }
}