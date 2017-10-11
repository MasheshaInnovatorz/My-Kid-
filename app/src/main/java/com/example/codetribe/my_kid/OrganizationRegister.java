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
    public  String uid;
    public String orgname;
    public String orgAddress;
    public String orgCity;
    public String orgEmail;
    public String orgPhoneNo;
    public String orgPassword;
    public Map<String,Boolean> stars = new HashMap<>();

    public OrganizationRegister() {
    }

    public OrganizationRegister(String uid, String orgname, String orgAddress, String orgCity, String orgEmail, String orgPhoneNo, String orgPassword) {
        this.uid = uid;
        this.orgname = orgname;
        this.orgAddress = orgAddress;
        this.orgCity = orgCity;
        this.orgEmail = orgEmail;
        this.orgPhoneNo = orgPhoneNo;
        this.orgPassword = orgPassword;

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

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String,Object> organizationSave = new HashMap<>();

        organizationSave.put("orguid", uid);
        organizationSave.put("orgName", orgname);
        organizationSave.put("orgAddres", orgAddress);
        organizationSave.put("orgCity", orgCity);
        organizationSave.put("orgEmail",orgEmail);
        organizationSave.put("orgPhoneNumber", orgPhoneNo);
        organizationSave.put("orgPassword",orgPassword);


        return organizationSave;
    }



}


