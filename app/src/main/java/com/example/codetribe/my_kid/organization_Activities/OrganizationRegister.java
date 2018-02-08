package com.example.codetribe.my_kid.organization_Activities;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by CodeTribe on 10/11/2017.
 */

@IgnoreExtraProperties
public class OrganizationRegister {
    private String uid;
    private String orgname;
    private String orgAddress;//add street name and postal code
    private String orgCity;
    private String orgProvince;
    private String orgEmail;
    private String orgPhoneNo;
    private String orgPassword;
    private String adminKey;
   // private String orgRegNumber;
    private String orgPostalCode;

    public Map<String, Boolean> stars = new HashMap<>();

    public OrganizationRegister() {
    }

    public OrganizationRegister(String uid, String orgname, String orgAddress, String orgCity, String orgProvince, String orgEmail, String orgPhoneNo, String orgPassword , String orgPostalCode, String adminKey) {
        this.uid = uid;
        this.orgname = orgname;
        this.orgAddress = orgAddress;
        this.orgCity = orgCity;
        this.orgProvince  =orgProvince;
        this.orgEmail = orgEmail;
        this.orgPhoneNo = orgPhoneNo;
        this.orgPassword = orgPassword;
      //  this.orgRegNumber =orgRegNumber;
        this.orgPostalCode=orgPostalCode;
        this.adminKey = adminKey;

    }

    public String getOrgProvince() {
        return orgProvince;
    }

    private String getUid() {
        return uid;
    }

    private String getOrgname() {
        return orgname;
    }

    private String getOrgAddress() {
        return orgAddress;
    }

    private String getOrgCity() {
        return orgCity;
    }

    private String getOrgEmail() {
        return orgEmail;
    }

    private String getOrgPhoneNo() {
        return orgPhoneNo;
    }

    private String getOrgPassword() {
        return orgPassword;
    }

  //  private String getorgRegNumber() {   return orgRegNumber;}

    private String getorgPostalCode() {   return orgPostalCode;}


    private String getAdminKey() {
        return adminKey;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> organizationSave = new HashMap<>();

        organizationSave.put("orguid", uid);
        organizationSave.put("orgName", orgname);
        organizationSave.put("orgAddres", orgAddress);
        organizationSave.put("orgCity", orgCity);
        organizationSave.put("orgProvince", orgProvince);
        organizationSave.put("emailUser", orgEmail);
        organizationSave.put("orgPhoneNumber", orgPhoneNo);
        organizationSave.put("orgPassword", orgPassword);
       // organizationSave.put("orgRegNumber", orgRegNumber);
        organizationSave.put("orgPostalCode", orgPostalCode);
        organizationSave.put("adminKey", adminKey);
        organizationSave.put("isVerified", "verified");
        organizationSave.put("role", "admin");


        return organizationSave;
    }


}


