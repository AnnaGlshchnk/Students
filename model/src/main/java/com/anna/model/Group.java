package com.anna.model;

import java.util.Date;
import java.util.List;

public class Group {

    private int groupId;
    private String name;
    private Date createDate;
    private Date finishDate;
    private List<Student> students;
    private int size = students.size();

    public Group(String name) {
        this.name = name;
    }

    public Group(int groupId, String name) {
        this.groupId = groupId;
        this.name = name;
    }

    public Group(int groupId, String name, Date createDate, Date finishDate, int size) {
        this.groupId = groupId;
        this.name = name;
        this.createDate = createDate;
        this.finishDate = finishDate;
        this.size = size;
    }

    public Group(int groupId, String name, Date createDate, Date finishDate, List<Student> students) {
        this.groupId = groupId;
        this.name = name;
        this.createDate = createDate;
        this.finishDate = finishDate;
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
}
