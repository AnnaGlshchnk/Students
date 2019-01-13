package com.anna.model;

import com.anna.model.json.View;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Student {

    @JsonView(View.Student.class)
    private int studentId;

    @JsonView({View.Student.class, View.GroupWithStudents.class})
    private String name;

    @JsonView({View.Student.class, View.GroupWithStudents.class})
    private String surname;

    @JsonView(View.Student.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @JsonView(View.Student.class)
    private Group group;

    public Student() {
    }

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

    public Student(String surname, Date birthDate, Group group) {
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
