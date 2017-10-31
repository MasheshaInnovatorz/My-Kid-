package com.example.codetribe.my_kid;

/**
 * Created by CodeTribe on 9/14/2017.
 */

public class Kids {
    String id;
    String teachersId;
    String name;
    String surname;
    String address;
    String idNumber;
    String parentid;
    String profilepic;
    String gender;
    String orgName;
    String kidsGrade;
    String kidsRegistered;

    //additional infromation
    String allergies;
    String dietRequirements;
    String doctorsRecomendations;
    int kidHeight;
    double bodyWeight;


    public Kids() {
    }


    public Kids(String id, String name, String surname, String address, String idNumber, String parentid, String kidsGrade, String kidsRegistered, String gender, String orgName) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.idNumber = idNumber;
        this.parentid = parentid;
        this.gender = gender;
        this.kidsGrade = kidsGrade;
        this.orgName = orgName;
        this.kidsRegistered = kidsRegistered;

    }


    public Kids(String id, String teachersId, String name, String surname, String address, String idNumber, String parentid, String gender) {
        this.id = id;

        this.name = name;
        this.surname = surname;
        this.address = address;
        this.idNumber = idNumber;
        this.teachersId = teachersId;
        this.parentid = parentid;
        this.gender = gender;
    }

    public String getOrgName() {
        return orgName;
    }

    public String getKidsGrade() {
        return kidsGrade;
    }

    public String getKidsRegistered() {
        return kidsRegistered;
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

    public String getTeachersId() {
        return teachersId;
    }


    // new constractor for medical information

    //public Kids(String allergies,String dietRequirements,String doctorsRecomendations,int kidHeight,double bodyWeight,String parentid,String id)

    {
        this.allergies = allergies;
        this.dietRequirements = dietRequirements;
        this.doctorsRecomendations = doctorsRecomendations;
        this.bodyWeight = bodyWeight;
        this.kidHeight = kidHeight;
        this.parentid = parentid;
    }


    public String getAllergies() {
        return allergies;
    }

    public String getDietRequirements() {
        return dietRequirements;
    }

    public String getDoctorsRecomendations() {
        return doctorsRecomendations;
    }

    public int getkidHeight() {
        return kidHeight;
    }

    public double getBodyWeight() {
        return bodyWeight;
    }

}
