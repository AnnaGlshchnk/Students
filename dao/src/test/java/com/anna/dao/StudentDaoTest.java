package com.anna.dao;

import com.anna.config.DaoTestConfig;
import com.anna.model.Group;
import com.anna.model.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoTestConfig.class)
@Transactional
public class StudentDaoTest {
    private static final Logger LOGGER = LogManager.getLogger(StudentDaoTest.class);

    @Autowired
    private StudentsDao studentsDao;

    @Test
    public void getStudents() {
        LOGGER.debug("service: getStudents");

        List<Student> students = studentsDao.getStudents(null, null);
        Assert.assertEquals(6, students.size());
    }

    @Test
    public void getStudentsWithParam() {
        LOGGER.debug("service: getStudents");

        List<Student> students = studentsDao.getStudents("1996-05-04", "1998-04-08");
        Assert.assertEquals(2, students.size());
    }

    @Test
    public void getStudentById() {
        LOGGER.debug("service: getStudentById");

        Student student = studentsDao.getStudentById(1);
        Assert.assertEquals("Ann", student.getName());
        Assert.assertEquals("Glush", student.getSurname());
    }

    @Test
    public void addStudent() throws ParseException {
        LOGGER.debug("service: addStudent");

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = simpleDateFormat.parse("1998-09-09");

        Student student = new Student("Val", "Ui", date, new Group(2));
        Integer studentId = studentsDao.addStudent(student);

        student = studentsDao.getStudentById(studentId);
        Assert.assertEquals(student.getStudentId(), 7);
    }

    @Test
    public void updateStudent() {
        LOGGER.debug("service: updateStudent");

        Student student = studentsDao.getStudentById(1);
        student.setName("Anna");

       studentsDao.updateStudent(student);
       student = studentsDao.getStudentById(1);
       Assert.assertEquals("Anna", student.getName());
    }

    @Test
    public void deleteStudent() {
        LOGGER.debug("service: deleteStudent");

        studentsDao.deleteStudent(1);
        List<Student> students = studentsDao.getStudents(null, null);
        Assert.assertEquals(5, students.size());

    }
}
