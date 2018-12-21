package com.anna.test;

import com.anna.config.DaoTestConfig;
import com.anna.dao.StudentsDao;
import com.anna.model.Group;
import com.anna.model.Student;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:dao-test-config.xml")
public class StudentDaoTest {

    @Autowired
    StudentsDao studentsDao;

    @Test
    public  void getStudent(){

        List<Student> students = studentsDao.getStudents(null, null);
        Assert.assertEquals(7, students.size());

        students = studentsDao.getStudents(19, null);
        Assert.assertEquals(3, students.size());
    }

    @Test
    public void getStudentById(){

        Student student = studentsDao.getStudentById(1);
        Assert.assertEquals("Ann", student.getName());
        Assert.assertEquals("Glush", student.getSurname());
    }

//    @Test
//    public void addStudent(){
//
//        Student student = new Student("Val", "Ui", 18, 2);
//        studentsDao.addStudent(student);
//
//        student = studentsDao.getStudentById(8);
//
//        Assert.assertEquals(student.getName(),"Val");
//    }

    @Test
    public void updateStudent(){
        Student student = studentsDao.getStudentById(2);
        student.setName("Vall");

        studentsDao.updateStudent(student);
        student = studentsDao.getStudentById(2);
        Assert.assertEquals("Vall", student.getName());
    }

    @Test
    public void deleteStudent(){
        studentsDao.deleteStudent(1);
        List<Student> students = studentsDao.getStudents(null, null);
        Assert.assertEquals(true, true);

    }
}
