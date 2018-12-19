package com.anna.model;

public class Student {

    private int studentId;
    private String name;
    private String surname;
    private int age;
    private int groupId;

    Group group;

    public Student(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Student() {
    }

    public Student(String name, String surname, int age, int groupId) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.groupId = groupId;
    }

    public Student(int studentId, String name, String surname, int age) {
        this.studentId = studentId;
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public Student(int studentId, String name, String surname, int age, Group group) {
        this.studentId = studentId;
        this.name = name;
        this.surname = surname;
        this.age = age;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
