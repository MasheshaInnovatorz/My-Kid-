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
    String grade;
    String yearRegistered;


    public Kids() {
    }

    public Kids(String id, String grade, String name, String surname, String address, String idNumber, String parentid, String gender, String yearRegistered) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.idNumber = idNumber;
        this.parentid = parentid;
        this.yearRegistered = yearRegistered;
        this.gender = gender;
        this.grade = grade;

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

    public String getGrade() {
        return grade;
    }

    public String getYearRegistered() {
        return yearRegistered;
    }
}


