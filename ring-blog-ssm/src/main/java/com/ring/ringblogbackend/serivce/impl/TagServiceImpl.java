package com.ring.ringblogbackend.serivce.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ring.ringblogbackend.dao.TagDao;
import com.ring.ringblogbackend.domain.Tag;
import com.ring.ringblogbackend.serivce.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao tagDao;

    @Override
    public List<Tag> findAll() {

        QueryWrapper<Tag> qw = new QueryWrapper<>();

        return tagDao.selectList(qw);
    }

    @Override
    public boolean findByName(String tagName) {
        LambdaQueryWrapper<Tag> lq = Wrappers.lambdaQuery();
        lq.eq(Tag::getTagName, tagName);
        Tag tag = tagDao.selectOne(lq);
        return tag != null;
    }

    @Override
    public void add(Tag tag) {
        tagDao.insert(tag);
    }

    @Override
    public void update(Tag tag) {
        tagDao.updateById(tag);
    }

    @Override
    public void delete(String id) {
        tagDao.deleteById(id);
    }

    @Override
    public void changeNumByName(String tagName, boolean flag) {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Tag::getTagName, tagName);
        Tag tag = tagDao.selectOne(wrapper);
        if (flag) {
            tag.setBlogNum(tag.getBlogNum() + 1);
        } else {
            tag.setBlogNum(tag.getBlogNum() - 1);
        }
        tagDao.updateById(tag);
    }

    @Override
    public void changeNumById(String id, boolean flag) {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Tag::getId, id);
        Tag tag = tagDao.selectOne(wrapper);
        if (flag) {
            tag.setBlogNum(tag.getBlogNum() + 1);
        } else {
            tag.setBlogNum(tag.getBlogNum() - 1);
        }
        tagDao.updateById(tag);
    }
}
