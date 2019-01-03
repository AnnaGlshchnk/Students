package com.anna.controller;

import com.anna.controller.wrappers.IdWrapper;
import com.anna.model.Group;
import com.anna.model.Student;
import com.anna.model.json.View;
import com.anna.service.StudentsService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class StudentsController {

    private final StudentsService studentService;

    @Autowired
    public StudentsController(StudentsService studentService) {
        this.studentService = studentService;
    }

    @JsonView(View.Student.class)
    @GetMapping("/students")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Student> getStudents(@RequestParam(value = "minBirthDate", required = false) String  minBirthDate,
                                   @RequestParam(value = "maxBirthDate", required = false) String  maxBirthDate) {
        return studentService.getStudents(minBirthDate, maxBirthDate);
    }

    @JsonView(View.StudentDetails.class)
    @GetMapping("/students/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Student getStudentById(@PathVariable(value = "studentId") Integer studentId) {
        return studentService.getStudentById(studentId);
    }

    @PostMapping("/students")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public IdWrapper addStudent(@RequestBody Student student) {
        return IdWrapper.wrap(studentService.addStudent(student));
    }

    @PostMapping("/students/{studentId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public void  updateGroup(@RequestBody Student student, @PathVariable("studentId") Integer studentId) {
        student.setStudentId(studentId);
        studentService.updateStudent(student);
    }

    @DeleteMapping("/students/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGroup(@PathVariable(value = "studentId") Integer studentId) {
        studentService.deleteStudent(studentId);
    }
}
