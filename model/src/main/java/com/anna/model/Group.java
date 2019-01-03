package com.anna.model;

import com.anna.model.json.View;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Group {

    @JsonView({View.Group.class, View.GroupWithStudents.class})
    private int groupId;

    @JsonView({View.Group.class, View.GroupWithStudents.class})
    private String name;

    @JsonView({View.Group.class, View.GroupWithStudents.class})
    private Date createDate;

    @JsonView({View.Group.class, View.GroupWithStudents.class})
    private Date finishDate;

    @JsonView(View.GroupWithStudents.class)
    private List<Student> students;

    @JsonView(View.Group.class)
    private int countOfStudent;

    @JsonView(View.Group.class)
    private float avgAge;

    public Group(int groupId) {
        this.groupId = groupId;
    }

    public Group(String name) {
        this.name = name;
    }

    public Group(int groupId, String name) {
        this.groupId = groupId;
        this.name = name;
    }

    public Group(int groupId, String name, Date createDate, Date finishDate) {
        this.groupId = groupId;
        this.name = name;
        this.createDate = createDate;
        this.finishDate = finishDate;
    }

    public Group(String name, Date createDate, Date finishDate) {
        this.name = name;
        this.createDate = createDate;
        this.finishDate = finishDate;
    }

    public Group(int groupId, String name, Date createDate, Date finishDate, List<Student> students) {
        this.groupId = groupId;
        this.name = name;
        this.createDate = createDate;
        this.finishDate = finishDate;
        this.students = students;
    }

    public Group(int groupId, String name, Date createDate, Date finishDate, int countOfStudent, float avgAge) {
        this.groupId = groupId;
        this.name = name;
        this.createDate = createDate;
        this.finishDate = finishDate;
        this.countOfStudent = countOfStudent;
        this.avgAge = avgAge;
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

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public int getCountOfStudent() {
        return countOfStudent;
    }

    public void setCountOfStudent(int countOfStudent) {
        this.countOfStudent = countOfStudent;
    }

    public float getAvgAge() {
        return avgAge;
    }

    public void setAvgAge(float avgAge) {
        this.avgAge = avgAge;
    }
}
