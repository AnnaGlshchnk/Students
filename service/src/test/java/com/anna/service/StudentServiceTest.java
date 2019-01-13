package com.anna.service;

import com.anna.exception.OperationFailedException;
import com.anna.model.Group;
import com.anna.model.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:service-test-config.xml"})
@Transactional
public class StudentServiceTest {
    private static final Logger LOGGER = LogManager.getLogger(StudentServiceTest.class);

    @Autowired
    private StudentsService studentsService;

    @Test
    public void getStudents() {
        LOGGER.debug("service: getStudents");

        List<Student> students = studentsService.getStudents(null, null);
        Assert.assertEquals(6, students.size());
    }

    @Test
    public void getStudentsWithParam() {
        LOGGER.debug("service: getStudents");

        List<Student> students = studentsService.getStudents("1996-05-04", "1998-04-08");
        Assert.assertEquals(2, students.size());
    }

    @Test
    public void getStudentById() {
        LOGGER.debug("service: getStudentById");

        Student student = studentsService.getStudentById(1);
        Assert.assertNotNull(student);
        Assert.assertNotEquals(0, student.getStudentId());
        Assert.assertEquals("Ann", student.getName());
        Assert.assertEquals("Glush", student.getSurname());
    }

    @Test
    public void addStudent() throws ParseException {
        LOGGER.debug("service: addStudent");

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = simpleDateFormat.parse("1998-09-09");

        Student student = new Student("Val", "Ui", date, new Group(2, "B"));
        Integer studentId = studentsService.addStudent(student);
        student = studentsService.getStudentById(studentId);
        Assert.assertThat(student, allOf(hasProperty("studentId", equalTo(7)),
                hasProperty("name", equalTo("Val")),
                hasProperty("surname", equalTo("Ui")),
                hasProperty("birthDate", equalTo(date))));
    }

    @Test
    public void updateStudent() {
        LOGGER.debug("service: updateStudent");

        Student student = studentsService.getStudentById(1);
        student.setName("Anna");
        studentsService.updateStudent(student);
        student = studentsService.getStudentById(1);

        Assert.assertEquals("Anna", student.getName());
    }

    @Test
    public void deleteStudent() {
        LOGGER.debug("service: deleteStudent");

        studentsService.deleteStudent(1);
        List<Student> students = studentsService.getStudents(null, null);
        Assert.assertEquals(5, students.size());

    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getStudentByIdException() throws Exception {
        LOGGER.debug("test: getStudentByIdException");

        studentsService.getStudentById(10);
    }

    @Test(expected = OperationFailedException.class)
    public void addStudentException() throws ParseException {
        LOGGER.debug("service: addStudentException");

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = simpleDateFormat.parse("1998-09-09");

        Student student = new Student( "Ui", date, new Group(2, "B"));
        studentsService.addStudent(student);
    }

    @Test(expected = OperationFailedException.class)
    public void updateStudentException() {
        LOGGER.debug("service: updateStudentException");

        Student student = new Student("Sally", "Fish");
        studentsService.updateStudent(student);
    }

    @Test(expected = OperationFailedException.class)
    public void deleteStudentException() {
        LOGGER.debug("service: deleteStudentException");

        studentsService.deleteStudent(10);

    }

}
