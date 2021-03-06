package com.anna.model;

import com.anna.model.json.View;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
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
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
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

  /**
   * Constructor for class SaveStudent.
   *
   * @param studentId this is the student's ID
   * @param name this is group name
   * @param surname this is the student's last name
   */
  public Student(int studentId, String name, String surname) {
    this.studentId = studentId;
    this.name = name;
    this.surname = surname;
  }

  /**
   * Constructor for class SaveStudent.
   *
   * @param name this is group name
   * @param surname this is the student's last name
   * @param birthDate this is the student's birth date
   * @param group this is the student's group
   */
  public Student(String name, String surname, Date birthDate, Group group) {
    this.name = name;
    this.surname = surname;
    this.birthDate = birthDate;
    this.group = group;
  }

  /**
   * Constructor for class SaveStudent.
   *
   * @param studentId this is the student's ID
   * @param name this is group name
   * @param surname this is the student's last name
   * @param birthDate this is the student's birth date
   * @param group this is the student's group
   */
  public Student(int studentId, String name, String surname, Date birthDate, Group group) {
    this.studentId = studentId;
    this.name = name;
    this.surname = surname;
    this.birthDate = birthDate;
    this.group = group;
  }

  /**
   * Constructor for class SaveStudent.
   *
   * @param surname this is the student's last name
   * @param birthDate this is the student's birth date
   * @param group this is the student's group
   */
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
