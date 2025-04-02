package com.meet.blog_post.tags.service;

import com.meet.blog_post.tags.dto.TagDto;
import org.springframework.http.ResponseEntity;

public interface TagService {
    ResponseEntity saveTag(TagDto tagDto);

    ResponseEntity findAll();
}
