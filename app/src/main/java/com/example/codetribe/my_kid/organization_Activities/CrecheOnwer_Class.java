package com.example.codetribe.my_kid.organization_Activities;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;


@IgnoreExtraProperties
public class CrecheOnwer_Class {
    private String adminUid;
    private String admiName;
    private String adminSurname;
    private String adminIdNo;
    private String adminGender;
    private String adminContact;
    private String adminRole;
    private String email;
    private String orgName;
    private String userCity;
    private String userOrgId;
    private String userProfilePic;

    public CrecheOnwer_Class() {
    }

    public CrecheOnwer_Class(String adminUid, String admiName, String adminSurname, String adminIdNo, String adminGender, String adminRole, String email, String orgName, String adminContact, String userCity, String userOrgId) {


        this.adminUid = adminUid;
        this.admiName = admiName;
        this.adminSurname = adminSurname;
        this.adminIdNo = adminIdNo;
        this.adminGender = adminGender;
        this.adminRole = adminRole;
        this.email = email;
        this.orgName = orgName;
        this.adminContact = adminContact;
        this.userCity = userCity;
        this.userOrgId = userOrgId;
        this.userProfilePic = " ";

    }



    public String getUserProfilePic() {
        return userProfilePic;
    }

    public void setUserProfilePic(String userProfilePic) {
        this.userProfilePic = userProfilePic;
    }

    private String getAdminUid() {
        return adminUid;
    }

    private String getAdmiName() {
        return admiName;
    }

    private String getAdminSurname() {
        return adminSurname;
    }

    private String getAdminIdNo() {
        return adminIdNo;
    }


    private String getAdminGender() {
        return adminGender;
    }

    private String getAdminRole() {
        return adminRole;
    }

    private String getEmail() {
        return email;
    }

    private String getOrgName() {
        return orgName;
    }

    private String getAdminContact() {
        return adminContact;
    }

    private String getUserCity() {
        return userCity;
    }

    public String getUserOrgId() {
        return userOrgId;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> adminSave = new HashMap<>();


        adminSave.put("userKey", adminUid);
        adminSave.put("userName", admiName);
        adminSave.put("userSurname", adminSurname);
        adminSave.put("userIdNumber", adminIdNo);
        adminSave.put("userCity", userCity);
        adminSave.put("userGender", adminGender);
        adminSave.put("role", adminRole);
        adminSave.put("emailUser", email);
        adminSave.put("orgName", orgName);
        adminSave.put("userContact", adminContact);
        adminSave.put("isVerified", "verified");
        adminSave.put("userAddress", "Egret");
        adminSave.put("userOrgId",userOrgId);
        adminSave.put("userProfilePic",userProfilePic);

        return adminSave;
    }
}
