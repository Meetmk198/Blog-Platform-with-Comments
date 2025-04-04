package com.meet.blog_post.comments.service;

import com.meet.blog_post.blogpost.repo.BlogPostRepo;
import com.meet.blog_post.category.dto.CategoryDto;
import com.meet.blog_post.category.model.Category;
import com.meet.blog_post.comments.dto.CommentDto;
import com.meet.blog_post.comments.model.Comment;
import com.meet.blog_post.comments.repo.CommentRepo;
import com.meet.blog_post.exception.ApplicationException;
import com.meet.blog_post.response.SuccessResponse;
import com.meet.blog_post.user.dto.UserDTO;
import com.meet.blog_post.user.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.meet.blog_post.common.constants.CmnConstants.getLoggedInUser;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepo commentRepo;
    @Autowired
    BlogPostRepo blogPostRepo;

    @Override
    public ResponseEntity saveComment(CommentDto commentDto) {
        User loggedInUser = getLoggedInUser();
        Comment comment = new Comment();
        comment.setBlogpost(blogPostRepo.findById(commentDto.getBlogpostId()).orElseThrow(() -> new ApplicationException("No blog post Found With Id " + commentDto.getBlogpostId(), HttpStatus.NOT_FOUND)));
        comment.setCommentText(commentDto.getCommentText());
        comment.setUser(loggedInUser);
        //check if Reply to Existing Comment or Whole new Comment. For New Comment parentId = -1 / Reply comment ParentId = parentComment.commentId
        Optional<Comment> parentComment = commentRepo.findById(commentDto.getParentCommentId());
        comment.setParentCommentId(parentComment.isPresent() ? parentComment.get().getCommentId() : -1L);
        commentRepo.save(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse(HttpStatus.CREATED, "Comment Saved Successfully"));
    }

    @Override
    public ResponseEntity getAllCommentsForBlogpost(Long blogpostId) {
        Optional<List<Comment>> tempLstComments = commentRepo.findByBlogpostBlogPostId(blogpostId);
        List<CommentDto> lstComments = new ArrayList<CommentDto>();
        if (tempLstComments.isPresent() && !tempLstComments.get().isEmpty()) {
            lstComments = tempLstComments.get().stream().map(comment ->
                    {
                        CommentDto commentDto = new CommentDto();
                        commentDto.setCommentId(comment.getCommentId());
                        commentDto.setCommentText(comment.getCommentText());
                        commentDto.setBlogpostId(comment.getBlogpost().getBlogPostId());
                        commentDto.setParentCommentId(comment.getParentCommentId());
                        UserDTO comUser = new UserDTO();
                        comUser.setUsername(comment.getUser().getUsername());
                        commentDto.setUserDTO(comUser);
                        return commentDto;
                    }
            ).collect(Collectors.toList());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse(HttpStatus.OK, "success",lstComments));
    }
}
