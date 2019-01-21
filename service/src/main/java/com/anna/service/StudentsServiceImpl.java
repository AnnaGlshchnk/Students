package com.anna.service;

import com.anna.dao.StudentsDao;
import com.anna.exception.OperationFailedException;
import com.anna.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public Integer addStudent(Student student) {
        validate(student);

        return studentsDao.addStudent(student);
    }

    @Override
    public void updateStudent(Student student) {

        if (student.getStudentId() <= 0) {
            throw new OperationFailedException("studentId should be more than 0");
        }
        validate(student);

        Integer result = studentsDao.updateStudent(student);
        if (result != 1) {
            throw new OperationFailedException("Operation Failed");
        }
    }

    private void validate(Student student) {
        if (student == null) {
            throw new OperationFailedException("student shouldn't be null");
        }
        if (student.getName() == null) {
            throw new OperationFailedException("student should have name");
        }
        if (student.getSurname() == null) {
            throw new OperationFailedException("student should have surname");
        }
        if (student.getBirthDate() == null) {
            throw new OperationFailedException("student should have birth date");
        }
        if (student.getGroup() == null) {
            throw new OperationFailedException("student should have group");
        }
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
