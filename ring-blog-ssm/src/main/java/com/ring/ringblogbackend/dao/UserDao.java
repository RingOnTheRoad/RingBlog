package com.ring.ringblogbackend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ring.ringblogbackend.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<User> {
}
