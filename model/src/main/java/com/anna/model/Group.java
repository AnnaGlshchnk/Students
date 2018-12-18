package com.anna.model;

import java.util.List;

public class Group {

    private int groupId;
    private String name;
    private int numberOfStudents;
    List<Student> students;

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
        this.numberOfStudents = numberOfStudents;
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

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }
}
