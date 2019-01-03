package com.anna.controller;

import com.anna.model.Student;
import com.anna.model.json.View;
import com.anna.service.StudentsService;
import com.anna.service.StudentsServiceImpl;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@CrossOrigin
@RestController
public class StudentsController {

    private StudentsService studentService;

    @Autowired
    public StudentsController(StudentsServiceImpl studentService) {
        this.studentService = studentService;
    }

    @JsonView(View.Student.class)
    @GetMapping("/students")
    @ResponseStatus(HttpStatus.OK)
    public List<Student> getStudents(@RequestParam(value = "minBirthDate", required = false) String  minBirthDate,
                                   @RequestParam(value = "maxBirthDate", required = false) String  maxBirthDate) {
        return studentService.getStudents(minBirthDate, maxBirthDate);
    }

    @JsonView(View.StudentDetails.class)
    @GetMapping("/students/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public Student getStudentById(@PathVariable(value = "studentId") Integer studentId) {
        return studentService.getStudentById(studentId);
    }

    @PostMapping("/students")
    public ResponseEntity<Void> addStudent(@RequestBody Student student, UriComponentsBuilder builder) {
        UriComponents uriComponents = builder.path("/students/{studentId}").buildAndExpand(student.getStudentId());
        return ResponseEntity.created(uriComponents.toUri()).build();

    }

    @PostMapping("/students/{studentId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
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
