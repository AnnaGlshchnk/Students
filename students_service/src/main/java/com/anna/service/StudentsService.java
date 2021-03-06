package com.anna.service;

import com.anna.exception.OperationFailedException;
import com.anna.model.SaveStudent;
import com.anna.model.Student;
import java.util.List;

public interface StudentsService {

  List<Student> getStudents(Integer size,
      Integer page,
      String minBirthDate,
      String maxBirthDate) throws OperationFailedException;

  Student getStudentById(Integer studentId) throws OperationFailedException;

  Integer addStudent(SaveStudent student) throws OperationFailedException;

  Integer updateStudent(Integer studentId, SaveStudent student) throws OperationFailedException;

  void deleteStudent(Integer studentId) throws OperationFailedException;
}

