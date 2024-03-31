package com.vn.chat.data;

public class User {

    private String id;
    private String code;
    private String user;
    private String password;
    private String name;
    private String email;
    private String avatarUrl;
    private String userId = "";
    private String status;

    public User() {
    }

    public User(String userId){
        this.userId = userId;
    }

    public User(String user, String password){
        this.user = user;
        this.password = password;
    }

    public User(String id, String user, String password, String fullName, String email) {
        this.id = id;
        this.user = user;
        this.password = password;
        this.name = fullName;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + user + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
