package com.example.codetribe.my_kid;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by CodeTribe on 10/11/2017.
 */

@IgnoreExtraProperties
public class CrecheOnwer_Class {
    public String adminUid;
    public String admiName;
    public String adminSurname;
    public String adminIdNo;
    public String adminGender;
    public String adminContact;
    public String adminRole;
    public String email;
    public String orgName;


    public CrecheOnwer_Class() {
    }

    public CrecheOnwer_Class(String adminUid, String admiName, String adminSurname, String adminIdNo, String adminGender, String adminRole, String email, String orgName, String adminContact) {
        this.adminUid = adminUid;
        this.admiName = admiName;
        this.adminSurname = adminSurname;
        this.adminIdNo = adminIdNo;
        this.adminGender = adminGender;
        this.adminRole = adminRole;
        this.email = email;
        this.orgName = orgName;
        this.adminContact = adminContact;

    }

    /*
     String uid;
    String name;
    String surname;
    String address;
    String idNumber;
    String parentid;
    String profilepic;
    String gender;
    String grade;
     */


    public String getAdminUid() {
        return adminUid;
    }

    public String getAdmiName() {
        return admiName;
    }

    public String getAdminSurname() {
        return adminSurname;
    }

    public String getAdminIdNo() {
        return adminIdNo;
    }


    public String getAdminGender() {
        return adminGender;
    }

    public String getAdminRole() {
        return adminRole;
    }

    public String getEmail() {
        return email;
    }

    public String getOrgName() {
        return orgName;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> adminSave = new HashMap<>();

        adminSave.put("userKey", adminUid);
        adminSave.put("userName", admiName);
        adminSave.put("userSurname", adminSurname);
        adminSave.put("userIdNumber", adminIdNo);
        adminSave.put("userGender", adminGender);
        adminSave.put("role", adminRole);
        adminSave.put("emailUser", email);
        adminSave.put("orgName", orgName);
        adminSave.put("userContact", adminContact);
        adminSave.put("isVerified", "verified");
        adminSave.put("userAddress", "Egret");


        return adminSave;
    }
}
