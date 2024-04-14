package com.ring.ringblogbackend.serivce.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ring.ringblogbackend.dao.BlogDao;
import com.ring.ringblogbackend.domain.Blog;
import com.ring.ringblogbackend.serivce.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogDao blogDao;

    @Override
    public Map<String, Object> findAllPage(int pageNum, int pageSize, String tagName, String state) {
        Page<Blog> page = new Page<>(pageNum, pageSize);
        if (!Objects.equals(tagName, "") && !Objects.equals(state, "null")) {
            LambdaQueryWrapper<Blog> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(Blog::getTagName, tagName);
            wrapper.eq(Blog::getState, state);
            List<Blog> blogList = blogDao.selectPage(page, wrapper).getRecords();
            Long total = blogDao.selectPage(page, wrapper).getTotal();
            HashMap<String, Object> map = new HashMap<>();
            map.put("blogs", blogList);
            map.put("total", total);
            return map;
        } else {
            QueryWrapper<Blog> wrapper = new QueryWrapper<>();
            List<Blog> blogList = blogDao.selectPage(page, wrapper).getRecords();
            Long total = blogDao.selectPage(page, wrapper).getTotal();
            HashMap<String, Object> map = new HashMap<>();
            map.put("blogs", blogList);
            map.put("total", total);
            return map;
        }
    }

    @Override
    public Blog findById(String id) {
        return blogDao.selectById(id);
    }

    @Override
    public byte[] findImgById(String id) {
        LambdaQueryWrapper<Blog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Blog::getId, id);
        Blog blog = blogDao.selectOne(wrapper);
        return blog.getCover();
    }

    @Override
    public void add(Blog blog) {
         blogDao.insert(blog);
    }

    @Override
    public void delete(String id) {
        blogDao.deleteById(id);
    }
}
