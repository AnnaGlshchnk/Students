package com.anna.dao;

import com.anna.model.Group;
import com.anna.model.Student;

import java.util.List;

public interface StudentsDao {

    List<Group> getGroups(Integer minStudent, Integer maxStudent);

    Group getGroupById();

    Integer addGroup(Group group);

    Integer updateGroup(Group group);

    Integer deleteGroup(Integer id);

    List<Student> getStudents(Integer minAge, Integer maxAge);

    Student getStudentById();

    Integer addStudent(Student student);

    Integer updateStudent(Student student);

    Integer deleteStudent(Integer id);

}
