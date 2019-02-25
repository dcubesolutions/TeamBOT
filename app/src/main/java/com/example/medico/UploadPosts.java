package com.example.medico;

import com.google.firebase.database.ServerValue;


public class UploadPosts extends BlogPostId{

    private String postKey;
    private String uploadImageUrl;
    private String uploadTitle;
    private String uploadSubject;
    private String uploadId;
    private Object timeStamp ;

    public Object getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }

    public UploadPosts(String uploadImageUrl, String uploadTitle, String uploadSubject, String uploadId) {
        this.uploadImageUrl = uploadImageUrl;
        this.uploadTitle = uploadTitle;
        this.uploadSubject = uploadSubject;
        this.uploadId = uploadId;
        this.timeStamp = ServerValue.TIMESTAMP;
    }

    public UploadPosts() {

    }

    public String getUploadImageUrl() {
        return uploadImageUrl;
    }

    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

    public void setUploadImageUrl(String uploadImageUrl) {
        this.uploadImageUrl = uploadImageUrl;
    }

    public String getUploadTitle() {
        return uploadTitle;
    }

    public void setUploadTitle(String uploadTitle) {
        this.uploadTitle = uploadTitle;
    }

    public String getUploadSubject() {
        return uploadSubject;
    }

    public void setUploadSubject(String uploadSubject) {
        this.uploadSubject = uploadSubject;
    }

    public String getPostKey() {
        return postKey;
    }

    public void setPostKey(String postKey) {
        this.postKey = postKey;
    }
}