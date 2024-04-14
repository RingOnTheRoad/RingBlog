package com.ring.ringblogbackend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ring.ringblogbackend.domain.Tag;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagDao extends BaseMapper<Tag> {
}
