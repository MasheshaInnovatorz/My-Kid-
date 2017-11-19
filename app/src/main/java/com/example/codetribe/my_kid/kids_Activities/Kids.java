package com.example.codetribe.my_kid.kids_Activities;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

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
    String gender;
    String orgName;
    String kidsGrade;
    String kidsRegistered;
    String profilePic;

    //additional infromation
    String allergies;
    String dietRequirements;
    String doctorsRecomendations;
    String kidHeight;
    String bodyWeight;


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
        this.allergies="";
        this.dietRequirements="";
        this.doctorsRecomendations="";
        this.kidHeight="";
        this.bodyWeight="";
        this.profilePic = "";

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
        this.allergies="";
        this. dietRequirements="";
        this .doctorsRecomendations="";
        this. kidHeight="";
        this. bodyWeight="";
        this.profilePic = "";
    }

    public String getOrgName() {
        return orgName;
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


    public String getGender() {
        return gender;
    }

    public String getTeachersId() {
        return teachersId;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getParentid() {
        return parentid;
    }

    public String getKidsGrade() {
        return kidsGrade;
    }

    public String getKidsRegistered() {
        return kidsRegistered;
    }

    public String getProfilePic() {
        return profilePic;
    }

    // new constractor for medical information
    public Kids(String allergies,String dietRequirements,String doctorsRecomendations,String kidHeight,String bodyWeight)

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

    public String getKidHeight() {
        return kidHeight;
    }

    public String getBodyWeight() {
        return bodyWeight;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("allergies", allergies);
        result.put("dietRequirements", dietRequirements);
        result.put("doctorsRecomendations", doctorsRecomendations);
        result.put("bodyWeight", bodyWeight);
        result.put("kidHeight", kidHeight);
        result.put("parentid", parentid);

        return result;
    }



}
