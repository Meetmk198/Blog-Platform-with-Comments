package com.meet.blog_post.tags.service;

import com.meet.blog_post.response.SuccessResponse;
import com.meet.blog_post.tags.dto.TagDto;
import com.meet.blog_post.tags.model.Tag;
import com.meet.blog_post.tags.repo.TagRepo;
import com.meet.blog_post.user.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.meet.blog_post.common.constants.CmnConstants.getLoggedInUser;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    TagRepo tagRepo;
    @Override
    public ResponseEntity saveTag(TagDto tagDto) {
        Tag tag = new Tag();
        tag.setTagName(tagDto.getTagName());
        tagRepo.save(tag);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse(HttpStatus.CREATED,"Tag Created Successfully."));
    }

    @Override
    public ResponseEntity findAll() {

        List<Tag> lstTags = tagRepo.findAll();
        if (!lstTags.isEmpty()){
        List<TagDto> lstTagDTOs = lstTags.stream().map(tag -> {
            TagDto tagDto = new TagDto();
            tagDto.setTagName(tag.getTagName());
            tagDto.setTagId(tag.getTagId());
            return tagDto;
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse(HttpStatus.OK,lstTagDTOs));
    }
    return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse(HttpStatus.OK,"No Tags Found."));
    }
}
