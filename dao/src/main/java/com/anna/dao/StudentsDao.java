package com.anna.dao;

import com.anna.model.Group;
import com.anna.model.Student;

import java.util.List;

public interface StudentsDao {

    List<Student> getStudents(Integer minAge, Integer maxAge);

    Student getStudentById(Integer studentId);

    Integer addStudent(Student student);

    Integer updateStudent(Student student);

    Integer deleteStudent(Integer studentId);

}
