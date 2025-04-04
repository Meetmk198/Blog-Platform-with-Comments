package com.meet.blog_post.common.models;

import com.meet.blog_post.user.dto.UserSessionInfo;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.util.Date;

import static com.meet.blog_post.common.constants.CmnConstants.STATUS_ACTIVE;
import static com.meet.blog_post.common.constants.CmnConstants.getUserSessionInfo;

@MappedSuperclass
public abstract class TimeStampedBaseEntity {

    @Column(name = "active_flag", nullable = false)
    private Short activeFlag;

    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @Column(name = "created_by_ip", nullable = false)
    private String createdByIp;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "updated_by_ip")
    private String updatedByIp;


    @PrePersist
    private void handleBeforePersist() {
        UserSessionInfo userSessionInfo = getUserSessionInfo();

        if (activeFlag == null) {
            activeFlag = STATUS_ACTIVE;
        }
        createdDate = new Date();
        createdByIp = userSessionInfo.getRemoteIpAddr();
    }

    @PreUpdate
    private void handleBeforeUpdate() {
        UserSessionInfo userSessionInfo = getUserSessionInfo();

        updatedDate = new Date();
        updatedByIp = userSessionInfo.getRemoteIpAddr();
    }

    public Short getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Short activeFlag) {
        this.activeFlag = activeFlag;
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

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedByIp() {
        return updatedByIp;
    }

    public void setUpdatedByIp(String updatedByIp) {
        this.updatedByIp = updatedByIp;
    }
}