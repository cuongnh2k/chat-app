package com.vn.chat.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Message {
    private String id;
    private String content;
    private String type;
    private String createdDate;
    private String lastModifiedDate;
    private String channelId;
    private String parentId;
    private User sender = new User();
    private List<File> messageFiles = new ArrayList<>();
    private List<File> files = new ArrayList<>();

    public Message() {
    }

    public Message(String channelId) {
        this.channelId = channelId;
    }

    public Message(String parentId, String content) {
        this.content = content;
        this.parentId = parentId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<File> getMessageFiles() {
        return messageFiles;
    }

    public void setMessageFiles(List<File> messageFiles) {
        this.messageFiles = messageFiles;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public void addFile(File file){
        if(this.files == null){
            this.files = new ArrayList<>();
        }
        this.files.add(file);
    }
}
