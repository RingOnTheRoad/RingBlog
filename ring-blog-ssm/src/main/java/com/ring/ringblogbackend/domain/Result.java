package com.ring.ringblogbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Result {

    private Integer code;
    private String msg;
    private Object data;

    public static Result success(Object data) {
        return new Result(1,"成功", data);
    }

    public static Result error(Object data) {
        return new Result(0,"失败", data);
    }

}
