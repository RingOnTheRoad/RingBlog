package com.ring.ringblogbackend.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class Tag {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String tagName;
    private Integer blogNum;

}
