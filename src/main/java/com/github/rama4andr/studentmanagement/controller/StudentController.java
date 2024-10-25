package com.github.rama4andr.studentmanagement.controller;

import com.github.rama4andr.studentmanagement.dto.StudentDto;
import com.github.rama4andr.studentmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // GET: Получение всех студентов, которые не помечены как удаленные
    @GetMapping("/get_all")
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        List<StudentDto> allStudents = studentService.getAllStudents();
        return ResponseEntity.ok(allStudents);
    }

    // POST: Добавление нового студента
    @PostMapping("/add")
    public ResponseEntity<StudentDto> addStudent(@RequestBody StudentDto studentDto) {
        StudentDto addedStudent = studentService.addStudent(studentDto);
        return ResponseEntity.ok(addedStudent);
    }

    // PUT: Обновление студента по ID
    @PutMapping("/update/{id}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable String id,
                                                    @RequestBody StudentDto studentDto) {
        StudentDto updatedStudent = studentService.updateStudent(id, studentDto);
        return ResponseEntity.ok(updatedStudent);
    }

    // DELETE: Пометка студента как удаленного
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
