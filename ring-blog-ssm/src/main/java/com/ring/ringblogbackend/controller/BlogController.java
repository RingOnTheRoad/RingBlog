package com.ring.ringblogbackend.controller;

import com.ring.ringblogbackend.domain.Blog;
import com.ring.ringblogbackend.domain.Result;
import com.ring.ringblogbackend.serivce.BlogService;
import com.ring.ringblogbackend.serivce.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

@RestController
@RequestMapping("/blog")
@CrossOrigin
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TagService tagService;

    @GetMapping
    public Result findAllPage(@RequestParam int pageNum, int pageSize, String tagName, String state) {
        return Result.success(blogService.findAllPage(pageNum, pageSize, tagName, state));
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable String id) {
        return Result.success(blogService.findById(id));
    }

    @GetMapping("/img/{id}")
    public byte[] findImgById(@PathVariable String id) {
        return blogService.findImgById(id);
    }

    @PostMapping
        public Result add(@RequestParam("title") String title, @RequestParam("tagName") String tag_id, @RequestParam("cover_img") MultipartFile cover_img, @RequestParam("content") String content, @RequestParam("state") Integer state) throws SQLException, IOException {
        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setTagName(tag_id);
        blog.setContent(content);
        blog.setState(state);
        blog.setCover(cover_img.getBytes());
        blog.setDate(new Timestamp(System.currentTimeMillis()));
        blogService.add(blog);
        tagService.changeNumByName(tag_id, true);
        return Result.success("添加博客成功");
    }

    @DeleteMapping
    public Result delete(@RequestBody String id) {
        blogService.delete(id);
        tagService.changeNumById(id, false);
        return Result.success("删除博客成功");
    }

}
