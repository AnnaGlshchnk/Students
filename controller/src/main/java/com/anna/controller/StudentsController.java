package com.anna.controller;

import com.anna.model.Student;
import com.anna.service.StudentsService;
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

    //@JsonView(View.CompanySummary.class)
    @GetMapping("/students")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Student> getStudents(@RequestParam(value = "minBirthDate", required = false) String  minBirthDate,
                                   @RequestParam(value = "maxBirthDate", required = false) String  maxBirthDate) {
        return studentService.getStudents(minBirthDate, maxBirthDate);
    }

    // @JsonView(View.CompanyDetails.class)
    @GetMapping("/students/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Student getStudentById(@PathVariable(value = "studentId") Integer studentId) {
        return studentService.getStudentById(studentId);
    }

    @DeleteMapping("/students/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGroup(@PathVariable(value = "studentId") Integer studentId) {
        studentService.deleteStudent(studentId);
    }
}
