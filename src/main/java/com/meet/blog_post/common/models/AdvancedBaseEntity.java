package com.meet.blog_post.common.models;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.meet.blog_post.user.dto.UserSessionInfo;
import com.meet.blog_post.user.models.User;
import jakarta.persistence.*;

import java.util.Date;

import static com.meet.blog_post.common.constants.CmnConstants.getLoggedInUser;
import static com.meet.blog_post.common.constants.CmnConstants.getUserSessionInfo;

@MappedSuperclass
public abstract class AdvancedBaseEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "updated_by_user_id")
    protected User updatedByUser;

    @Column(name = "updated_date")
    protected Date updatedDate;

    @Column(name = "updated_by_ip")
    protected String updatedByIp;

    @PreUpdate
    private void handleBeforeUpdate() {
        UserSessionInfo userSessionInfo = getUserSessionInfo();

        updatedByUser = getLoggedInUser();
        updatedDate = new Date();
        updatedByIp = userSessionInfo.getRemoteIpAddr();
    }

    public User getUpdatedByUser() {
        return updatedByUser;
    }

    public void setUpdatedByUser(User updatedByUser) {
        this.updatedByUser = updatedByUser;
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
