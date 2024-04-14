package com.ring.ringblogbackend.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Blog {
    // 传输id用String防止精度丢失
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String title;
    private String tagName;
    private Integer state;
    private byte[] cover;
    private String content;
    // 格式化日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp date;

}
