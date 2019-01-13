package com.anna.service;

import com.anna.dao.StudentsDao;
import com.anna.exception.OperationFailedException;
import com.anna.model.Group;
import com.anna.model.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

public class StudentServiceMockTest {

    private static final Logger LOGGER = LogManager.getLogger(StudentServiceMockTest.class);

    private StudentsService studentsService;

    private StudentsDao mockStudentsDao;

    public StudentServiceMockTest() {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("service-mock-test-config.xml");
        studentsService = context.getBean(StudentsService.class);
        mockStudentsDao = context.getBean(StudentsDao.class);
    }

    @After
    public void clean() {
        verify(mockStudentsDao);
        reset(mockStudentsDao);
    }

    @Test
    public void getStudents() {
        LOGGER.debug("service: getStudents");

        List<Student> students = new ArrayList<>();
        expect(mockStudentsDao.getStudents(null, null)).andReturn(students);

        replay(mockStudentsDao);

        students = studentsService.getStudents(null, null);
        Assert.assertEquals(0, students.size());
    }

    @Test
    public void getStudentsWithParam() {
        LOGGER.debug("service: getStudentsWithParam");

        List<Student> students = new ArrayList<>();
        expect(mockStudentsDao.getStudents("1998-03-04", "2000-12-12")).andReturn(students);

        replay(mockStudentsDao);

        students = studentsService.getStudents("1998-03-04", "2000-12-12");
        Assert.assertEquals(0, students.size());
    }

    @Test
    public void getStudentById() {
        LOGGER.debug("service: getStudentById");

        expect(mockStudentsDao.getStudentById(anyInt())).andReturn(new Student(4));
        replay(mockStudentsDao);

        Student student = studentsService.getStudentById(4);
        assertEquals(4, student.getStudentId());
    }

    @Test
    public void addGroup() throws ParseException {
        LOGGER.debug("service: addGroup");

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = simpleDateFormat.parse("1998-09-09");

        Student student = new Student("Val", "Ui", date, new Group(2, "B"));

        expect(mockStudentsDao.addStudent(student)).andReturn(7);
        replay(mockStudentsDao);

        Integer studentId = studentsService.addStudent(student);
        assertEquals(studentId, (Integer) 7);
    }

    @Test
    public void updateGroup() throws ParseException {
        LOGGER.debug("service: updateGroup");
        expect(mockStudentsDao.updateStudent(anyObject())).andReturn(1);
        replay(mockStudentsDao);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = simpleDateFormat.parse("1998-09-09");

        Integer studentId = studentsService.updateStudent(new Student(1, "Anna", "Glush", date, new Group(1, "A")));
        Assert.assertEquals(studentId, Integer.valueOf(1));

    }

    @Test
    public void deleteGroup() {
        LOGGER.debug("service: deleteGroup");

        expect(mockStudentsDao.deleteStudent(anyInt())).andReturn(1);
        replay(mockStudentsDao);

        studentsService.deleteStudent(1);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getStudentByIdException() {
        LOGGER.debug("service: getStudentByIdException");

        expect(mockStudentsDao.getStudentById(anyInt())).andReturn(null);
        replay(mockStudentsDao);

        studentsService.getStudentById(22);
    }

    @Test(expected = OperationFailedException.class)
    public void deleteGroupExc() {
        LOGGER.debug("service: deleteGroup");

        expect(mockStudentsDao.deleteStudent(anyInt())).andReturn(0);
        replay(mockStudentsDao);

        studentsService.deleteStudent(1);
    }
}
