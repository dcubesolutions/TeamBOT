package com.example.medico.Model;

public class User {
    private String fName;
    private String lName;
    private String mid;
    private String email;
    private String id;
    private String imageUrl;
    private String status;
    private String Degree;
    private String ClininiNo;
    private String Category;
    private Boolean isVerified;

    public User() {
    }

    public User(String fName, String lName, String mid, String email, String id, String imageUrl, String status, String degree, String clininiNo, String category, Boolean isVerified) {
        this.fName = fName;
        this.lName = lName;
        this.mid = mid;
        this.email = email;
        this.id = id;
        this.imageUrl = imageUrl;
        this.status = status;
        this.Degree = degree;
        this.ClininiNo = clininiNo;
        this.Category = category;
        this.isVerified = isVerified;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDegree() {
        return Degree;
    }

    public void setDegree(String degree) {
        Degree = degree;
    }

    public String getClininiNo() {
        return ClininiNo;
    }

    public void setClininiNo(String clininiNo) {
        ClininiNo = clininiNo;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

}
