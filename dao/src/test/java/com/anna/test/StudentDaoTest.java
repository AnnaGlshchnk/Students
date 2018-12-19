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
@ContextConfiguration(classes = DaoTestConfig.class)
public class StudentDaoTest {

    @Autowired
    StudentsDao studentsDao;

    @Test
    public void getGroups(){

        List<Group> groups = studentsDao.getGroups(null, null);
        Assert.assertEquals(3, groups.size());

        groups = studentsDao.getGroups(1, 2);
        Assert.assertEquals(2, groups.size());
    }

    @Test
    public void  getGroupById(){
        Group group = studentsDao.getGroupById(1);
        Assert.assertEquals("A", group.getName());

    }

    @Test
    public void addGroups(){

        Group group = new Group("D");
        studentsDao.addGroup(group);

        Assert.assertEquals(group.getName(), "D");
    }


    @Test
    public void updateGroup(){

        Group group = studentsDao.getGroupById(1);
        group.setName("new A");

        studentsDao.updateGroup(group);
        group = studentsDao.getGroupById(1);
        Assert.assertEquals("new A", group.getName());
    }

    @Test
    public  void deleteGroup(){

        studentsDao.deleteGroup(1);
        Group group = studentsDao.getGroupById(1);
        Assert.assertNull(group.getGroupId());

    }

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

    @Test
    public void addStudent(){

        Student student = new Student("Val", "Ui", 18, 2);
        studentsDao.addStudent(student);

        student = studentsDao.getStudentById(8);
        Assert.assertEquals(student.getName(),"Val");
    }

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
        Assert.assertEquals();

    }
}
