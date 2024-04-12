package com.vn.chat.data;

import java.util.ArrayList;
import java.util.List;

public class Channel {

    private String id;
    private String name = "";
    private String nickname;
    private String email;
    private String avatarUrl = "";
    private String type;
    private List<String> userIds = new ArrayList<>();
    private Message latestMessage;
    private String userId;
    private String channelId;
    private String status;
    private String search;
    private String ownerId;
    private boolean isRequest = false;
    private boolean isAccept = false;
    private boolean isCreateGroup = false;
    private boolean isCancel = false;
    private boolean isAdmin = false;
    private boolean isChangeAdmin = false;

    public Channel() {
    }

    public Channel(String id){
        this.id = id;
        this.channelId = id;
    }

    public Channel(String userId, String name, String email, boolean isRequest) {
        this.name = name;
        this.email = email;
        this.userId = userId;
        this.isRequest = isRequest;
    }

    public Channel(String userId, String name, String email, boolean isRequest, boolean isAccept) {
        this.name = name;
        this.email = email;
        this.userId = userId;
        this.isRequest = isRequest;
        this.isAccept = isAccept;
    }

    public String getId() {
        if(id == null && channelId != null) return channelId;
        else if(id != null && channelId == null) return id;
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Message getLatestMessage() {
        return latestMessage;
    }

    public void setLatestMessage(Message latestMessage) {
        this.latestMessage = latestMessage;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public boolean isRequest() {
        return isRequest;
    }

    public void setRequest(boolean request) {
        isRequest = request;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isAccept() {
        return isAccept;
    }

    public void setAccept(boolean accept) {
        isAccept = accept;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isCreateGroup() {
        return isCreateGroup;
    }

    public void setCreateGroup(boolean createGroup) {
        isCreateGroup = createGroup;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public boolean isCancel() {
        return isCancel;
    }

    public void setCancel(boolean cancel) {
        isCancel = cancel;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isChangeAdmin() {
        return isChangeAdmin;
    }

    public void setChangeAdmin(boolean changeAdmin) {
        isChangeAdmin = changeAdmin;
    }
}
