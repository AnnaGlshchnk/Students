package com.anna.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class SaveStudent {

    @Size(min = 2, max = 15)
    @NotNull
    private String name;

    @Size(min = 2, max = 15)
    @NotNull
    private String surname;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Date birthDate;

    @NotNull
    private Group group;

    public SaveStudent() {
    }

    public SaveStudent(String name, String surname, Date birthDate, Group group) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.group = group;
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
