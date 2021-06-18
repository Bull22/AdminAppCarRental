package com.example.adminappcarrental.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String Username;
    private String Email;
    private String Phonenumber;
    private String Dateofbirth;
    private String Password;
    private String avatar;
    private String address;

    public User() {

    }

    public User(String username, String email, String phonenumber, String dateofbirth, String password, String avatar, String address) {
        Username = username;
        Email = email;
        Phonenumber = phonenumber;
        Dateofbirth = dateofbirth;
        Password = password;
        this.avatar = avatar;
        this.address = address;
    }

    protected User(Parcel in) {
        Username = in.readString();
        Email = in.readString();
        Phonenumber = in.readString();
        Dateofbirth = in.readString();
        Password = in.readString();
        avatar = in.readString();
        address = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhonenumber() {
        return Phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        Phonenumber = phonenumber;
    }

    public String getDateofbirth() {
        return Dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        Dateofbirth = dateofbirth;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Username);
        dest.writeString(Email);
        dest.writeString(Phonenumber);
        dest.writeString(Dateofbirth);
        dest.writeString(Password);
        dest.writeString(avatar);
        dest.writeString(address);
    }
}
