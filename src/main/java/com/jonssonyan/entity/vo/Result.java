package com.jonssonyan.entity.vo;

import lombok.Data;

@Data
public class Result {
    private Integer code;
    private String msg;
    private Object data;

    public static Result success(Object data) {
        Result result = new Result();
        result.setCode(1);
        result.setMsg("成功");
        result.setData(data);
        return result;
    }

    public static Result success() {
        return success(null);
    }

    public static Result fail(String msg) {
        Result result = new Result();
        result.setCode(0);
        result.setMsg(msg);
        return result;
    }

    public static Result fail(Integer code, String msg, Object object) {
        Result result = new Result();
        result.setCode(0);
        result.setMsg(msg);
        result.setData(object);
        return result;
    }
}