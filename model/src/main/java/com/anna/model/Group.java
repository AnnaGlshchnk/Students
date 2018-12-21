package com.anna.model;

import java.util.Date;
import java.util.List;

public class Group {

    private int groupId;
    private String name;
    List<Student> students;

    Date createDate;
    Date graduateDate;

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Group(String name) {
        this.name = name;
    }

    public Group(int groupId, String name) {
        this.groupId = groupId;
        this.name = name;
    }

    public Group(int groupId, String name, int numberOfStudents) {
        this.groupId = groupId;
        this.name = name;
    }

    public Group(int groupId, String name, List<Student> students) {
        this.groupId = groupId;
        this.name = name;
        this.students = students;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
