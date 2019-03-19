package com.anna.service;

import com.anna.dao.StudentsDao;
import com.anna.exception.OperationFailedException;
import com.anna.model.SaveStudent;
import com.anna.model.Student;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StudentsServiceImpl implements StudentsService {

  private StudentsDao studentsDao;

  @Autowired
  public StudentsServiceImpl(StudentsDao studentsDao) {
    this.studentsDao = studentsDao;
  }

  @Override
  public List<Student> getStudents(String minBirthDate, String maxBirthDate) {

    return studentsDao.getStudents(minBirthDate, maxBirthDate);
  }

  @Override
  public Student getStudentById(Integer studentId) {
    if (studentId == null) {
      throw new OperationFailedException("studentId shouldn't be null");
    }

    return studentsDao.getStudentById(studentId);
  }

  @Override
  public Integer addStudent(SaveStudent student) {
    return studentsDao.addStudent(student);
  }

  @Override
  public Integer updateStudent(Integer studentId, SaveStudent student) {
    return studentsDao.updateStudent(studentId, student);
  }

  @Override
  public void deleteStudent(Integer studentId) {
    if (studentId == null) {
      throw new OperationFailedException("studentId shouldn't be null");
    }

    Integer result = studentsDao.deleteStudent(studentId);
    if (result != 1) {
      throw new OperationFailedException("Operation Failed");
    }
  }
}
