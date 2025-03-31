package com.meet.blog_post.exception;

import org.springframework.http.HttpStatus;

public record ErrorResponse(HttpStatus status, Object message, long timestamp) {

    public ErrorResponse(HttpStatus status, Object message) {
        this(status, message, System.currentTimeMillis());
    }
}
