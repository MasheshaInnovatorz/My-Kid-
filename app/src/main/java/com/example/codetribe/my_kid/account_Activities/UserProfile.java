package com.example.codetribe.my_kid.account_Activities;

/**
 * Created by CodeTribe on 9/1/2017.
 */


public class UserProfile {
    private String keyUser;
    private String userName;
    private String userSurname;
    private String userIdNumber;
    private String userAddress;
    private String userprovince;
    private String userCity;
    private String userContact;
    private String userGender;
    private String isVerified;
    private String userProfilePic;
    private String userOrgId;

    //constructor
    public UserProfile() {
    }

    //constructor with parameters
    public UserProfile(String keyUser, String userName, String userSurname, String userIdNumber, String userAddress, String userContact, String userGender, String userCity, String userprovince, String isVerified) {
        this.keyUser = keyUser;
        this.userName = userName;
        this.userSurname = userSurname;
        this.userIdNumber = userIdNumber;
        this.userAddress = userAddress;
        this.userCity = userCity;
        this.userprovince = userprovince;
        this.userContact = userContact;
        this.userGender = userGender;
        this.isVerified = isVerified;
        this.userProfilePic = "";
    }




    public UserProfile(String keyUser, String userName, String userSurname, String userAddress, String userCity, String userContact) {
        this.keyUser = keyUser;
        this.userName = userName;
        this.userSurname = userSurname;
        this.userAddress = userAddress;
        this.userCity = userCity;
        this.userContact = userContact;
        this.userProfilePic = "";
    }

    public void setKeyUser(String keyUser) {
        this.keyUser = keyUser;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public void setuserprovince(String userprovince) {
        this.userprovince = userprovince;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }


    public void setUserContact(String userContact) {
        this.userContact = userContact;
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

    public String getuserprovince() {
        return userprovince;
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

    public String getUserProfilePic() {
        return userProfilePic;
    }

    public String getUserprovince() {
        return userprovince;
    }
}

