package com.anna.test;

import com.anna.model.Group;
import com.anna.model.Student;
import com.anna.service.StudentsService;
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
@ContextConfiguration(locations = {"classpath:service-test-config.xml"})
@Transactional
public class StudentServiceTest {
    private static final Logger LOGGER = LogManager.getLogger(GroupServiceTest.class);

    @Autowired
    StudentsService studentsService;

    @Test
    public void getStudents() {
        LOGGER.debug("test: getStudents");

        List<Student> students = studentsService.getStudents(null, null);
        Assert.assertEquals(6, students.size());
    }

    @Test
    public void getStudentsWithParam() {
        LOGGER.debug("test: getStudents");

        List<Student> students = studentsService.getStudents("1996-05-04", "1998-04-08");
        Assert.assertEquals(2, students.size());
    }

    @Test
    public void getStudentById() {
        LOGGER.debug("test: getStudentById");

        Student student = studentsService.getStudentById(1);
        Assert.assertNotNull(student);
        Assert.assertNotEquals(0, student.getStudentId());
        Assert.assertEquals("Ann", student.getName());
        Assert.assertEquals("Glush", student.getSurname());
    }

    @Test
    public void addStudent() throws ParseException {
        LOGGER.debug("test: addStudent");

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = simpleDateFormat.parse("1998-09-09");

        Student student = new Student("Val", "Ui", date, new Group(2, "B"));
        Integer studentId = studentsService.addStudent(student);
        Assert.assertNotNull(student);
        Assert.assertNotNull(student.getName());
        Assert.assertNotNull(student.getSurname());
        Assert.assertNotNull(student.getBirthDate());
        Assert.assertNotNull(student.getGroup());

        student = studentsService.getStudentById(studentId);
        Assert.assertEquals(student.getStudentId(), 7);
    }

    @Test
    public void updateStudent() {
        LOGGER.debug("test: updateStudent");

        Student student = studentsService.getStudentById(1);
        Assert.assertNotNull(student);
        Assert.assertNotEquals(0, student.getStudentId());
        Assert.assertNotNull(student.getName());
        Assert.assertNotNull(student.getSurname());
        Assert.assertNotNull(student.getBirthDate());
        Assert.assertNotNull(student.getGroup());
        student.setName("Anna");

        studentsService.updateStudent(student);
        student = studentsService.getStudentById(1);
        Assert.assertEquals("Anna", student.getName());
    }

    @Test
    public void deleteStudent() {
        LOGGER.debug("test: deleteStudent");

        studentsService.deleteStudent(1);
        List<Student> students = studentsService.getStudents(null, null);
        Assert.assertEquals(5, students.size());

    }
}
