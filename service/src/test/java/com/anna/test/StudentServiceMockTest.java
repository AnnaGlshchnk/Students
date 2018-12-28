package com.anna.test;

import com.anna.dao.StudentsDao;
import com.anna.model.Group;
import com.anna.model.Student;
import com.anna.service.StudentsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:service-mock-test-config.xml"})
public class StudentServiceMockTest {

    private static final Logger LOGGER = LogManager.getLogger(GroupServiceMockTest.class);

    @Autowired
    private StudentsService studentsService;

    @Autowired
    private StudentsDao mockStudentsDao;

    @After
    public void clean() {
        verify(mockStudentsDao);
        reset(mockStudentsDao);
    }

    @Test
    public void getStudents() {
        LOGGER.debug("test: getStudents");

        List<Student> students = new ArrayList<>();
        expect(mockStudentsDao.getStudents(null, null)).andReturn(students);

        replay(mockStudentsDao);

        students = studentsService.getStudents(null, null);
        Assert.assertEquals(0, students.size());
    }

    @Test
    public void getStudentsWithParam() {
        LOGGER.debug("test: getStudentsWithParam");

        List<Student> students = new ArrayList<>();
        expect(mockStudentsDao.getStudents("1998-03-04", "2000-12-12")).andReturn(students);

        replay(mockStudentsDao);

        students = studentsService.getStudents("1998-03-04", "2000-12-12");
        Assert.assertEquals(0, students.size());
    }

    @Test
    public void getStudentById() {
        LOGGER.debug("test: getStudentById");

        expect(mockStudentsDao.getStudentById(anyInt())).andReturn(new Student(4));
        replay(mockStudentsDao);

        Student student = studentsService.getStudentById(4);
        assertEquals(4, student.getStudentId());
    }

    @Test
    public void addGroup() throws ParseException {
        LOGGER.debug("test: addGroup");

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
        LOGGER.debug("test: updateGroup");
        expect(mockStudentsDao.updateStudent(anyObject())).andReturn(1);
        replay(mockStudentsDao);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = simpleDateFormat.parse("1998-09-09");

        Integer studentId = studentsService.updateStudent(new Student(1, "Anna","Glush", date, new Group(1, "A")));
        Assert.assertEquals(studentId, new Integer(1));

    }

    @Test
    public void deleteGroup() {
        LOGGER.debug("test: deleteGroup");

        expect(mockStudentsDao.deleteStudent(anyInt())).andReturn(null);
        replay(mockStudentsDao);

        Integer studentId = studentsService.deleteStudent(1);
        Assert.assertNull(studentId);

    }
}
