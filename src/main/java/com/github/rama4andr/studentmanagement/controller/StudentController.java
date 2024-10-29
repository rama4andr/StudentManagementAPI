package com.github.rama4andr.studentmanagement.controller;

import com.github.rama4andr.studentmanagement.dto.StudentDto;
import com.github.rama4andr.studentmanagement.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // GET: Получение всех студентов, которые не помечены как удаленные
    @GetMapping()
    public ResponseEntity<List<StudentDto>> getAll() {
        List<StudentDto> allStudents = studentService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(allStudents);
    }

    @PostMapping()
    public ResponseEntity<StudentDto> add(@RequestBody StudentDto studentDto) {
        StudentDto addedStudent = studentService.add(studentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedStudent);
    }

    // PUT: Обновление студента по ID
    @PatchMapping("/{id}")
    public ResponseEntity<StudentDto> update(@PathVariable String id,
                                             @RequestBody StudentDto studentDto) {
        StudentDto updatedStudent = studentService.update(id, studentDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedStudent);
    }

    // DELETE: Пометка студента как удаленного
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        studentService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

