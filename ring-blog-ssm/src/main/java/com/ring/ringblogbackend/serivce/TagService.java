package com.ring.ringblogbackend.serivce;

import com.ring.ringblogbackend.domain.Tag;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface TagService {

    /**
     * 查询所有
     */
    public List<Tag> findAll();


    /**
     * 根据名称查询
     */
    public boolean findByName(String tagName);

    /**
     * 添加分类
     */
    public void add(Tag tag);

    /**
     * 修改分类名称
     */
    public void update(Tag tag);

    /**
     * 删除分类
     */
    public void delete(String id);

    /**
     * 根据分类名称和标记改变博客数量
     */
    public void changeNumByName(String tagName, boolean flag);

    /**
     * 根据分类Id和标记改变博客数量
     */
    public void changeNumById(String id, boolean flag);


}
