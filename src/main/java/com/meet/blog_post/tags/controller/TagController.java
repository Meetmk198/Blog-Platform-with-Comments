package com.meet.blog_post.tags.controller;

import com.meet.blog_post.tags.dto.TagDto;
import com.meet.blog_post.tags.service.TagService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    TagService tagService;

    @PostMapping
    public ResponseEntity saveTag(@RequestBody @Valid TagDto tagDto) {
        return tagService.saveTag(tagDto);
    }
    @GetMapping
    public ResponseEntity getAllTags() {
        return tagService.findAll();
    }

}
