package com.anna.service;

import com.anna.config.ServiceTestConfig;
import com.anna.dao.StudentsDao;
import com.anna.exception.OperationFailedException;
import com.anna.model.Group;
import com.anna.model.SaveStudent;
import com.anna.model.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceTestConfig.class)
public class StudentServiceMockTest {
    private static final Logger LOGGER = LogManager.getLogger(StudentServiceMockTest.class);

    @InjectMocks
    private StudentsServiceImpl studentsService;

    @Mock
    private StudentsDao mockStudentsDao;

    public StudentServiceMockTest() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void clean() {
        Mockito.reset(mockStudentsDao);
    }

    @Test
    public void getStudents() {
        LOGGER.debug("service: getStudents");

        List<Student> students = new ArrayList<>();
        Mockito.when(mockStudentsDao.getStudents(null, null)).thenReturn(students);

        students = studentsService.getStudents(null, null);
        Assert.assertEquals(0, students.size());
    }

    @Test
    public void getStudentsWithParam() {
        LOGGER.debug("service: getStudentsWithParam");

        List<Student> students = new ArrayList<>();
        Mockito.when(mockStudentsDao.getStudents("1998-03-04", "2000-12-12")).thenReturn(students);

        students = studentsService.getStudents("1998-03-04", "2000-12-12");
        Assert.assertEquals(0, students.size());
    }

    @Test
    public void getStudentById() {
        LOGGER.debug("service: getStudentById");

        Mockito.when(mockStudentsDao.getStudentById(Mockito.any(Integer.class))).thenReturn(new Student(4));

        Student student = studentsService.getStudentById(4);
        assertEquals(4, student.getStudentId());
    }

    @Test
    public void addStudent() throws ParseException {
        LOGGER.debug("service: addStudent");

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = simpleDateFormat.parse("1998-09-09");

        SaveStudent student = new SaveStudent("Val", "Ui", date, new Group(2));

        Mockito.when(mockStudentsDao.addStudent(student)).thenReturn(7);

        Integer studentId = studentsService.addStudent(student);
        assertEquals(studentId, (Integer) 7);
    }

    @Test
    public void updateStudent() throws ParseException {
        LOGGER.debug("service: updateStudent");

        Mockito.when(mockStudentsDao.updateStudent(Mockito.any(Integer.class), Mockito.any(SaveStudent.class))).thenReturn(1);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = simpleDateFormat.parse("1998-09-09");

        Integer studentId = studentsService.updateStudent(1, new SaveStudent("Anna", "Glush", date, new Group(1)));
        Assert.assertEquals(studentId, Integer.valueOf(1));
    }

    @Test
    public void deleteStudent() {
        LOGGER.debug("service: deleteStudent");

        Mockito.when(mockStudentsDao.deleteStudent(Mockito.any(Integer.class))).thenReturn(1);

        studentsService.deleteStudent(1);
    }

    @Test(expected = RuntimeException.class)
    public void getStudentByIdException() {
        LOGGER.debug("service: getStudentByIdException");

        Mockito.when(mockStudentsDao.getStudentById(22)).thenThrow(new RuntimeException());

        studentsService.getStudentById(22);
    }

    @Test(expected = OperationFailedException.class)
    public void deleteStudentException() {
        LOGGER.debug("service: deleteStudentException");

        Mockito.when(mockStudentsDao.deleteStudent(22)).thenReturn(0);

        studentsService.deleteStudent(1);
    }

}
