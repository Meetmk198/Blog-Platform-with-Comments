package com.meet.blog_post.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResponse {

    private HttpStatus status;
    private Object message;
    private Long timestamp;
    private Object data;

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public SuccessResponse() {

    }

    public SuccessResponse(HttpStatus status, Object message) {
        this.status = status;
        this.message = message;
    }

    public SuccessResponse(HttpStatus status, Object message, long timestamp, Object data) {
        this.status = status;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
        this.data = data;
    }

    public SuccessResponse(HttpStatus status, Object message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
