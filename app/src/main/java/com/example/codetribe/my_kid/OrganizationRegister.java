package com.example.codetribe.my_kid;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by CodeTribe on 10/11/2017.
 */

@IgnoreExtraProperties
public class OrganizationRegister {
    public String uid;
    public String orgname;
    public String orgAddress;//add street name and postal code
    public String orgCity;
    public String orgEmail;
    public String orgPhoneNo;
    public String orgPassword;
    public String adminKey;
    public Map<String, Boolean> stars = new HashMap<>();

    public OrganizationRegister() {
    }

    public OrganizationRegister(String uid, String orgname, String orgAddress, String orgCity, String orgEmail, String orgPhoneNo, String orgPassword, String adminKey) {
        this.uid = uid;
        this.orgname = orgname;
        this.orgAddress = orgAddress;
        this.orgCity = orgCity;
        this.orgEmail = orgEmail;
        this.orgPhoneNo = orgPhoneNo;
        this.orgPassword = orgPassword;
        this.adminKey = adminKey;

    }


    public String getUid() {
        return uid;
    }

    public String getOrgname() {
        return orgname;
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public String getOrgCity() {
        return orgCity;
    }

    public String getOrgEmail() {
        return orgEmail;
    }

    public String getOrgPhoneNo() {
        return orgPhoneNo;
    }

    public String getOrgPassword() {
        return orgPassword;
    }

    public String getAdminKey() {
        return adminKey;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> organizationSave = new HashMap<>();

        organizationSave.put("orguid", uid);
        organizationSave.put("orgName", orgname);
        organizationSave.put("orgAddres", orgAddress);
        organizationSave.put("orgCity", orgCity);
        organizationSave.put("emailUser", orgEmail);
        organizationSave.put("orgPhoneNumber", orgPhoneNo);
        organizationSave.put("orgPassword", orgPassword);
        organizationSave.put("adminKey", adminKey);
        organizationSave.put("isVerified", "verified");
        organizationSave.put("role", "admin");


        return organizationSave;
    }


}


