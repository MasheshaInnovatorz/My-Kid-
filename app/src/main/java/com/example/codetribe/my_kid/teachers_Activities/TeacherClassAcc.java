package com.example.codetribe.my_kid.teachers_Activities;

/**
 * Created by mudau on 10/10/2017.
 */


public class TeacherClassAcc {
    private String userName;
    private String userSurname;
    private String userContact;
    private String teacherClassroom;
    private String userIdNumber;
    private String userGender;
    private String userKey;
    private String emailUser;
    private String passWordUser;
    private String role;
    private String isVerified;
    private String orgName;
    private String userAddress;
    private String userCity;
    private String userProfilePic;



    public TeacherClassAcc(String userName, String userSurname, String userContact, String teacherClassroom, String userIdNumber, String userGender, String userKey, String emailUser, String passWordUser, String role, String isVerified, String orgName, String userAddress, String userCity) {
        this.userName = userName;
        this.userSurname = userSurname;
        this.userContact = userContact;
        this.teacherClassroom = teacherClassroom;
        this.userIdNumber = userIdNumber;
        this.userGender = userGender;
        this.userKey = userKey;
        this.emailUser = emailUser;
        this.passWordUser = passWordUser;
        this.role = role;
        this.isVerified = isVerified;
        this.orgName = orgName;
        this.userAddress = userAddress;
        this.userCity = userCity;
        this.userProfilePic = "";

    }

    public TeacherClassAcc() {
    }

    public String getUserCity() {
        return userCity;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public String getOrgName() {
        return orgName;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public String getUserContact() {
        return userContact;
    }

    public String getTeacherClassroom() {
        return teacherClassroom;
    }

    public String getUserIdNumber() {
        return userIdNumber;
    }

    public String getUserGender() {
        return userGender;
    }

    public String getUserKey() {
        return userKey;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public String getPassWordUser() {
        return passWordUser;
    }

    public String getRole() {
        return role;
    }

    public String getIsVerified() {
        return isVerified;
    }

    public String getUserProfilePic() {
        return userProfilePic;
    }
}
