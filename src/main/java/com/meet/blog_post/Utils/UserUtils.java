package com.meet.blog_post.Utils;

import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtils {

    public static String getUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
