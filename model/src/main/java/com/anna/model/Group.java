package com.anna.model;

import com.anna.model.json.View;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Group {

  @JsonView({View.Student.class, View.GroupWithStudents.class})
  private int groupId;

  @JsonView({View.Student.class, View.GroupWithStudents.class})
  private String name;

  @JsonView(View.GroupWithStudents.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date createDate;

  @JsonView(View.GroupWithStudents.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date finishDate;

  @JsonView(View.GroupWithStudents.class)
  private List<Student> students;

  @JsonView(View.Group.class)
  private int countOfStudent;

  @JsonView(View.Group.class)
  private float avgAge;

  public Group() {
  }

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

  /**
   * Constructor for class Group.
   * @param groupId  this is group ID
   * @param name  this is group name
   * @param createDate  this is the date the group was created
   * @param finishDate  this is the graduation date
   */
  public Group(int groupId, String name, Date createDate, Date finishDate) {
    this.groupId = groupId;
    this.name = name;
    this.createDate = createDate;
    this.finishDate = finishDate;
  }

  /**
   * Constructor for class Group.
   * @param name  this is group name
   * @param createDate  this is the date the group was created
   * @param finishDate  this is the graduation date
   */
  public Group(String name, Date createDate, Date finishDate) {
    this.name = name;
    this.createDate = createDate;
    this.finishDate = finishDate;
  }

  /**
   * Constructor for class Group.
   * @param groupId  this is group ID
   * @param name  this is group name
   * @param createDate  this is the date the group was created
   * @param finishDate  this is the graduation date
   * @param students  this is list of students
   */
  public Group(int groupId, String name, Date createDate, Date finishDate, List<Student> students) {
    this.groupId = groupId;
    this.name = name;
    this.createDate = createDate;
    this.finishDate = finishDate;
    this.students = students;
  }

  /**
   * Constructor for class Group.
   * @param groupId  this is group ID
   * @param name  this is group name
   * @param createDate  this is the date the group was created
   * @param finishDate  this is the graduation date
   * @param countOfStudent  this is the number of students in a group
   * @param avgAge this is the average age of students
   */
  public Group(int groupId, String name, Date createDate,
      Date finishDate, int countOfStudent, float avgAge) {
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
