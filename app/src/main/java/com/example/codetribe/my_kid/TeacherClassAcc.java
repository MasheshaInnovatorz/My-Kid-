package com.example.codetribe.my_kid;

/**
 * Created by mudau on 10/10/2017.
 */

public class TeacherClassAcc {
    private String teacherName;
    private String teacherSurname;
    private String teacherContact;
    private String teacherClassroom;
    private String teacherIdnumber;
    private String teacherGender;
    private String keyteacher;

    public TeacherClassAcc(String teacherName, String teacherSurname, String teacherContact, String teacherClassroom, String teacherIdnumber, String teacherGender, String keyteacher) {
        this.teacherName = teacherName;
        this.teacherSurname = teacherSurname;
        this.teacherContact = teacherContact;
        this.teacherClassroom = teacherClassroom;
        this.teacherIdnumber = teacherIdnumber;
        this.teacherGender = teacherGender;
        this.keyteacher = keyteacher;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherSurname() {
        return teacherSurname;
    }

    public void setTeacherSurname(String teacherSurname) {
        this.teacherSurname = teacherSurname;
    }

    public String getTeacherContact() {
        return teacherContact;
    }

    public void setTeacherContact(String teacherContact) {
        this.teacherContact = teacherContact;
    }

    public String getTeacherClassroom() {
        return teacherClassroom;
    }

    public void setTeacherClassroom(String teacherClassroom) {
        this.teacherClassroom = teacherClassroom;
    }

    public String getTeacherIdnumber() {
        return teacherIdnumber;
    }

    public void setTeacherIdnumber(String teacherIdnumber) {
        this.teacherIdnumber = teacherIdnumber;
    }

    public String getTeacherGender() {
        return teacherGender;
    }

    public void setTeacherGender(String teacherGender) {
        this.teacherGender = teacherGender;
    }

    public String getKeyteacher() {
        return keyteacher;
    }

    public void setKeyteacher(String keyteacher) {
        this.keyteacher = keyteacher;
    }


}