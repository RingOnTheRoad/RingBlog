package com.ring.ringblogbackend.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;


@Data
public class User {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String account;
    private String password;
    private String description;
    private byte[] avatar;
    @TableField(exist = false)
    private String token;

}
