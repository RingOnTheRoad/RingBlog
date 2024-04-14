package com.ring.ringblogbackend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ring.ringblogbackend.domain.Blog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BlogDao extends BaseMapper<Blog> {
}
