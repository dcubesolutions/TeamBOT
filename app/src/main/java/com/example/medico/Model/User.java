package com.example.medico.Model;

public class User {
    String fName;
    String lName;
    String mid;
    String email;
    String id;
    String imageUrl;
    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public User(String fName, String lName, String mid, String email, String id, String imageUrl) {
        this.fName = fName;
        this.lName = lName;
        this.mid = mid;
        this.email = email;
        this.id = id;
        this.imageUrl = imageUrl;
    }

    public User() {
    }


}