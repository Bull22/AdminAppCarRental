package com.example.adminappcarrental.Model;

public class Admin {
private String nameAdmin;
private String phoneNumber;
private String passWord;
public Admin(){}

    public Admin(String nameAdmin, String phoneNumber, String passWord) {
        this.nameAdmin = nameAdmin;
        this.phoneNumber = phoneNumber;
        this.passWord = passWord;
    }

    public String getNameAdmin() {
        return nameAdmin;
    }

    public void setNameAdmin(String nameAdmin) {
        this.nameAdmin = nameAdmin;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
