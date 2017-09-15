package com.example.codetribe.my_kid;

/**
 * Created by CodeTribe on 9/14/2017.
 */

public class Kids {
    String id;
    String name;
    String surname;
    String address;
    String idNumber;
    String parentid;
    String profilepic;
    String gender;

    public Kids() {
    }

    public Kids(String id, String name, String surname, String address, String idNumber, String parentid, String profilepic, String gender) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.idNumber = idNumber;
        this.parentid = parentid;
        this.profilepic = profilepic;
        this.gender = gender;
    }

    public Kids(String id, String name, String surname, String address, String idNumber, String parentid, String gender) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.idNumber = idNumber;
        this.parentid = parentid;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAddress() {
        return address;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getParentid() {
        return parentid;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public String getGender() {
        return gender;
    }
}
