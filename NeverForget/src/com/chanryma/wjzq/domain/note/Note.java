package com.chanryma.wjzq.domain.note;

import java.util.ArrayList;
import java.util.List;

import com.chanryma.wjzq.domain.attachment.Attachment;
import com.chanryma.wjzq.domain.user.User;
import com.chanryma.wjzq.util.ListUtil;

public class Note {
    private int id;
    private int parentId = -1;
    private long createdOn;
    private User createdBy;
    private String memo;
    private int lockTime;
    private String address;
    private String longitude;
    private String latitude;
    private List<Attachment> attachments;

    public Note(User createdBy, String memo) {
        this.createdBy = createdBy;
        this.memo = memo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getLockTime() {
        return lockTime;
    }

    public void setLockTime(int lockTime) {
        this.lockTime = lockTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public List<Attachment> getAttachments() {
        return ListUtil.getSafeList(attachments);
    }

    public void addAttachment(Attachment attachment) {
        if (attachments == null) {
            attachments = new ArrayList<>();
        }

        attachments.add(attachment);
    }
}
