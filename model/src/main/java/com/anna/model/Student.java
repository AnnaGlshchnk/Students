package com.anna.model;

import java.util.Date;

public class Student {

    private int studentId;
    private String name;
    private String surname;
    private Date birthDate;
    private Group group;

    public Student(int studentId) {
        this.studentId = studentId;
    }

    public Student(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Student(int studentId, String name, String surname) {
        this.studentId = studentId;
        this.name = name;
        this.surname = surname;
    }

    public Student(String name, String surname, Date birthDate, Group group) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.group = group;
    }

    public Student(int studentId, String name, String surname, Date birthDate, Group group) {
        this.studentId = studentId;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.group = group;
    }



    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
