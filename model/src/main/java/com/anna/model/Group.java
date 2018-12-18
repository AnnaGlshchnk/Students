package com.anna.model;

public class Group {

    private  int groupId;
    private  String name;
    private int numberOfStudents;

    public Group() {
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
