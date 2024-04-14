package com.ring.ringblogbackend.controller;

import com.ring.ringblogbackend.domain.Result;
import com.ring.ringblogbackend.domain.Tag;
import com.ring.ringblogbackend.serivce.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/tags")
@CrossOrigin
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping
    public Result findAll() {
        return Result.success(tagService.findAll());
    }

    @PostMapping
    public Result add(@RequestBody Tag tag) {
        boolean exitTag = tagService.findByName(tag.getTagName());
        if (exitTag) {
            return Result.error("已存在此Tag");
        } else {
            tagService.add(tag);
            return Result.success("成功添加Tag");
        }
    }

    @PutMapping
    public Result update(@RequestBody Tag tag) {
        boolean exitTag = tagService.findByName(tag.getTagName());
        if (exitTag) {
            return Result.error("已存在此Tag");
        } else {
            tagService.update(tag);
            return Result.success("成功修改Tag");
        }
    }

    @DeleteMapping
    public Result delete(@RequestBody Tag tag) {
        tagService.delete(tag.getId().toString());
        return Result.success("成功删除Tag");
    }
}
