package com.anna.controller;

import com.anna.model.SaveStudent;
import com.anna.model.Student;
import com.anna.model.json.View;
import com.anna.service.StudentsService;
import com.fasterxml.jackson.annotation.JsonView;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@CrossOrigin
@RestController
public class StudentsController {

  private StudentsService studentService;

  @Autowired
  public StudentsController(StudentsService studentService) {
    this.studentService = studentService;
  }

  /**
   * Get student's list.
   *
   * @param page number of page
   * @param size number of students per page
   * @param minBirthDate min value for sort
   * @param maxBirthDate max value for sort
   * @return list
   */
  @JsonView(View.Student.class)
  @GetMapping(value = "/students")
  @ResponseStatus(HttpStatus.OK)
  public List<Student> getStudents(
      @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
      @RequestParam(value = "size", required = false, defaultValue = "5") Integer size,
      @RequestParam(value = "minBirthDate", required = false) String minBirthDate,
      @RequestParam(value = "maxBirthDate", required = false) String maxBirthDate) {
    return studentService.getStudents(page, size, minBirthDate, maxBirthDate);
  }

  @JsonView(View.StudentDetails.class)
  @GetMapping("/students/{studentId}")
  @ResponseStatus(HttpStatus.OK)
  public Student getStudentById(@PathVariable(value = "studentId") Integer studentId) {
    return studentService.getStudentById(studentId);
  }

  /**
   * Add student.
   *
   * @param student the student
   * @param builder builder for UriComponents
   * @return url
   */
  @PostMapping("/students")
  public ResponseEntity<Void> addStudent(@Valid @RequestBody SaveStudent student,
      UriComponentsBuilder builder) {
    Integer createdId = studentService.addStudent(student);
    UriComponents uriComponents = builder.path("/students/{studentId}").buildAndExpand(createdId);
    return ResponseEntity.created(uriComponents.toUri()).build();
  }

  @PutMapping("/students/{studentId}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void updateGroup(@Valid @RequestBody SaveStudent student,
      @PathVariable("studentId") Integer studentId) {
    studentService.updateStudent(studentId, student);
  }

  @DeleteMapping("/students/{studentId}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteGroup(@PathVariable(value = "studentId") Integer studentId) {
    studentService.deleteStudent(studentId);
  }

}
