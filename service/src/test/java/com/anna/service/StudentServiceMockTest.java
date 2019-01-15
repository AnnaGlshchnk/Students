package com.anna.service;

import com.anna.dao.StudentsDao;
import com.anna.exception.OperationFailedException;
import com.anna.model.Group;
import com.anna.model.Student;
import junit.framework.AssertionFailedError;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.SQLException;
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
    public void addStudent() throws ParseException {
        LOGGER.debug("service: addStudent");

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = simpleDateFormat.parse("1998-09-09");

        Student student = new Student("Val", "Ui", date, new Group(2));

        expect(mockStudentsDao.addStudent(student)).andReturn(7);
        replay(mockStudentsDao);

        Integer studentId = studentsService.addStudent(student);
        assertEquals(studentId, (Integer) 7);
    }

    @Test
    public void updateStudent() throws ParseException {
        LOGGER.debug("service: updateStudent");
        expect(mockStudentsDao.updateStudent(anyObject())).andReturn(1);
        replay(mockStudentsDao);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = simpleDateFormat.parse("1998-09-09");

       studentsService.updateStudent(new Student(1, "Anna", "Glush", date, new Group(1)));

    }

    @Test
    public void deleteStudent() {
        LOGGER.debug("service: deleteStudent");

        expect(mockStudentsDao.deleteStudent(anyInt())).andReturn(1);
        replay(mockStudentsDao);

        studentsService.deleteStudent(1);
    }

    @Test(expected = RuntimeException.class)
    public void getStudentByIdException() {
        LOGGER.debug("service: getStudentByIdException");

        expect(mockStudentsDao.getStudentById(22)).andThrow(new RuntimeException());
        replay(mockStudentsDao);

        studentsService.getStudentById(22);
    }

    @Test(expected = OperationFailedException.class)
    public void deleteStudentException() {
        LOGGER.debug("service: deleteStudentException");

        expect(mockStudentsDao.deleteStudent(anyInt())).andReturn(0);
        replay(mockStudentsDao);

        studentsService.deleteStudent(1);
    }

    @Test
    public void addStudentException() throws ParseException {
        LOGGER.debug("service: addStudentException");

        //setup
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = simpleDateFormat.parse("1998-09-09");

        Student student = new Student("Ui", date, new Group(2));

        //when
        try{
            studentsService.addStudent(student);
            throw new RuntimeException("Method should throw exception");
        } catch (OperationFailedException ex){
        }
        //then
        //check that mockStudentsDao.add was not called
        expect(mockStudentsDao.addStudent(student)).andReturn(7);
        expectLastCall().anyTimes();
        replay(mockStudentsDao);
    }

    @Test(expected = OperationFailedException.class)
    public void updateStudentException() throws ParseException {
        LOGGER.debug("service: updateStudentException");
        expect(mockStudentsDao.updateStudent(anyObject())).andReturn(0);
        replay(mockStudentsDao);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = simpleDateFormat.parse("1998-09-09");

        studentsService.updateStudent(new Student(10, "Anna", "Glush", date, new Group(1)));

    }

}
