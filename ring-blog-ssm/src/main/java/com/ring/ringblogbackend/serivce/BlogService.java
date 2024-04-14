package com.ring.ringblogbackend.serivce;

import com.ring.ringblogbackend.domain.Blog;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Transactional
public interface BlogService {

    /**
     * 分页查询(带搜索)
     */
    public Map<String, Object> findAllPage(int pageNum, int pageSize, String tagName, String state);

    /**
     * 根据ID查询
     */
    public Blog findById(String id);

    /**
     * 根据ID查询图片
     */
    public byte[] findImgById(String id);

    /**
     * 添加博客
     */
    public void add(Blog blog);

    /**
     * 删除博客
     */
    public void delete(String id);

}
