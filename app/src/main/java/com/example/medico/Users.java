package com.example.medico;

public class Users {
    String fName;
    String lName;
    String mid;
    String email;
    String id;


    public Users(){


    }
    public Users(String fName,String lName,String mid,String email,String id)
    {
        this.fName=fName;
        this.lName=lName;
        this.email=email;
        this.mid=mid;
        this.id=id;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getMid() {
        return mid;
    }

    public String getEmail() {
        return email;
    }
    public String getId(){
        return id;
    }

}
