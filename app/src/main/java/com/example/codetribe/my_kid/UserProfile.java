package com.example.codetribe.my_kid;

/**
 * Created by CodeTribe on 9/1/2017.
 */

public class UserProfile {
    private String name;
    private String surnmame;
    private String ownersId;
    private String address;
    private String city;
    private String cellPhone;
    private String gender;

    public UserProfile() {
    }

    public UserProfile(String name, String surnmame, String ownersId, String address, String city, String cellPhone, String gender) {
        this.name = name;
        this.surnmame = surnmame;
        this.ownersId = ownersId;
        this.address = address;
        this.city = city;
        this.cellPhone = cellPhone;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public String getSurnmame() {
        return surnmame;
    }

    public String getOwnersId() {
        return ownersId;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public String getGender() {
        return gender;
    }
}

