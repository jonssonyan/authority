package com.jonssonyan.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    private static final long serialVersionUID = 5208655401792192682L;
    private Integer code;
    private String msg;
    private Object data;

    public static Result success(Object data) {
        Result result = new Result();
        result.setCode(20000);
        result.setMsg("ok");
        result.setData(data);
        return result;
    }

    public static Result success() {
        return success(null);
    }

    public static Result fail(String msg) {
        Result result = new Result();
        result.setCode(50000);
        result.setMsg(msg);
        return result;
    }

    public static Result fail(Integer code, String msg, Object object) {
        Result result = new Result();
        result.setCode(50000);
        result.setMsg(msg);
        result.setData(object);
        return result;
    }
}