package com.meet.blog_post.user.dto;

public class UserSessionInfo {

    private String remoteIpAddr;
    private String remoteHostName;
    private UserDTO userDTO;

    public String getRemoteIpAddr() {
        return remoteIpAddr;
    }

    public void setRemoteIpAddr(String remoteIpAddr) {
        this.remoteIpAddr = remoteIpAddr;
    }

    public String getRemoteHostName() {
        return remoteHostName;
    }

    public void setRemoteHostName(String remoteHostName) {
        this.remoteHostName = remoteHostName;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}
