package com.anna.test;

import com.anna.dao.StudentsDao;
import com.anna.model.Group;
import com.anna.model.Student;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:dao-test-config.xml")
public class StudentDaoTest {

    @Autowired
    StudentsDao studentsDao;

    @Test
    public void getStudent() {

        List<Student> students = studentsDao.getStudents(null, null);
        Assert.assertEquals(6, students.size());
    }

    @Test
    public void getStudentWithParam() {

        List<Student> students = studentsDao.getStudents("1996-05-04", "1998-04-08");
        Assert.assertEquals(2, students.size());
    }

    @Test
    public void getStudentById() {

        Student student = studentsDao.getStudentById(1);
        Assert.assertEquals("Ann", student.getName());
        Assert.assertEquals("Glush", student.getSurname());
    }

    @Test
    public void addStudent() throws ParseException {

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
        Student student = studentsDao.getStudentById(1);
        student.setName("Anna");

        studentsDao.updateStudent(student);
        Student newstudent = studentsDao.getStudentById(1);
        Assert.assertEquals("Anna", newstudent.getName());
    }

    @Test
    public void deleteStudent() {
        studentsDao.deleteStudent(1);
        List<Student> students = studentsDao.getStudents(null, null);
        Assert.assertEquals(5, students.size());

    }
}
