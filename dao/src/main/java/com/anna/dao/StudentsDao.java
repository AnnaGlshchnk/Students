package com.anna.dao;

import com.anna.model.Student;

import java.util.Date;
import java.util.List;

public interface StudentsDao {

    List<Student> getStudents(String minBirthDate, String maxBirthDate);

    Student getStudentById(Integer studentId);

    Integer addStudent(Student student);

    Integer updateStudent(Student student);

    Integer deleteStudent(Integer studentId);

}
