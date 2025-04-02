package com.meet.blog_post.common.models;

import com.meet.blog_post.user.dto.UserSessionInfo;
import com.meet.blog_post.user.models.User;
import jakarta.persistence.*;

import java.util.Date;

import static com.meet.blog_post.common.constants.CmnConstants.*;
@MappedSuperclass
public class BaseEntity {

    @Column(name = "active_flag", nullable = false)
    protected Short activeFlag;

    @ManyToOne
    @JoinColumn(name = "created_by_user_id")
    protected User createdByUser;

    @Column(name = "created_date", nullable = false)
    protected Date createdDate;

    @Column(name = "created_by_ip", nullable = false)
    protected String createdByIp;

    @PrePersist
    private void handleBeforePersist() {
        UserSessionInfo userSessionInfo = getUserSessionInfo();

        if (activeFlag == null) {
            activeFlag = STATUS_ACTIVE;
        }
        createdByUser = getLoggedInUser();
        createdDate = new Date();
        createdByIp = userSessionInfo.getRemoteIpAddr();
    }

    public Short getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Short activeFlag) {
        this.activeFlag = activeFlag;
    }

    public User getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(User createdByUser) {
        this.createdByUser = createdByUser;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedByIp() {
        return createdByIp;
    }

    public void setCreatedByIp(String createdByIp) {
        this.createdByIp = createdByIp;
    }
}
