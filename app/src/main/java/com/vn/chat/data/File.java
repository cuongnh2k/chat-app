package com.vn.chat.data;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class File {
    private String id;
    private String url;
    private String name;
    private Integer size;
    private String contentType;
    private String accessModifier = "PUBLIC";
    private String fileUri;
    private String channelId;

    public File() {
    }

    public File(String fileUri) {
        this.fileUri = fileUri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getAccessModifier() {
        return accessModifier;
    }

    public void setAccessModifier(String accessModifier) {
        this.accessModifier = accessModifier;
    }

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }

    public RequestBody getBodyAccessModifier () {
        return RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(this.accessModifier));
    }
    public MultipartBody.Part getBodyFile(){
        if(this.fileUri != null && this.fileUri.length() > 0){
            java.io.File file = new java.io.File(this.fileUri);
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            return MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        }else{
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), "");
            return MultipartBody.Part.createFormData("file", "", requestFile);
        }
    }
}
