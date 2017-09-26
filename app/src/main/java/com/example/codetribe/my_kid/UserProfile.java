package com.example.codetribe.my_kid;

/**
 * Created by CodeTribe on 9/1/2017.
 */


public class UserProfile {
    private String keyUser;
    private String userName;
    private String userSurname;
    private String userIdNumber;
    private String userAddress;
    private String userCity;
    private String userContact;
    private String userGender;
    private String isVerified;

    public UserProfile() {
    }

    public UserProfile(String keyUser, String userName, String userSurname, String userIdNumber, String userAddress, String userCity, String userContact, String userGender, String isVerified) {
        this.keyUser = keyUser;
        this.userName = userName;
        this.userSurname = userSurname;
        this.userIdNumber = userIdNumber;
        this.userAddress = userAddress;
        this.userCity = userCity;
        this.userContact = userContact;
        this.userGender = userGender;
        this.isVerified = isVerified;
    }


    public String getKeyUser() {
        return keyUser;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public String getUserIdNumber() {
        return userIdNumber;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public String getUserCity() {
        return userCity;
    }

    public String getUserContact() {
        return userContact;
    }

    public String getUserGender() {
        return userGender;
    }

    public String getIsVerified() {
        return isVerified;
    }
}

