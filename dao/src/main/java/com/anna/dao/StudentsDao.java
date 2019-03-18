package com.anna.dao;

import com.anna.model.SaveStudent;
import com.anna.model.Student;
import java.util.List;

public interface StudentsDao {

  List<Student> getStudents(String minBirthDate, String maxBirthDate);

  Student getStudentById(Integer studentId);

  Integer addStudent(SaveStudent student);

  Integer updateStudent(Integer studentId, SaveStudent student);

  Integer deleteStudent(Integer studentId);

}
