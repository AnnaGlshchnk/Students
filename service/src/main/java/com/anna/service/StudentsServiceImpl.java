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

    @Autowired
    StudentsDao studentsDao;

    public void GroupsServiceImpl(StudentsDao studentsDao) {
        this.studentsDao = studentsDao ;
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
        toCheck(student);

        return studentsDao.addStudent(student);
    }

    @Override
    public Integer updateStudent(Student student) {
        toCheck(student);

        return studentsDao.updateStudent(student);
    }

    private void toCheck(Student student) {
        if (student == null) {
            throw new OperationFailedException("student shouldn't be null");
        }
        if (student.getStudentId() <= 0) {
            throw new OperationFailedException("studentId should be more than 0");
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
    public Integer deleteStudent(Integer studentId) {
        if (studentId == null) {
            throw new OperationFailedException("studentId shouldn't be null");
        }

        return studentsDao.deleteStudent(studentId);
    }
}
