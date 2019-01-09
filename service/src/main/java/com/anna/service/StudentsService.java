package com.anna.service;

import com.anna.exception.OperationFailedException;
import com.anna.model.Student;

import java.util.List;

public interface StudentsService {

    List<Student> getStudents(String minBirthDate, String maxBirthDate) throws OperationFailedException;

    Student getStudentById(Integer studentId) throws OperationFailedException;

    Integer addStudent(Student student) throws OperationFailedException;

    Integer updateStudent(Student student) throws OperationFailedException;

    Integer deleteStudent(Integer studentId) throws OperationFailedException;
}

