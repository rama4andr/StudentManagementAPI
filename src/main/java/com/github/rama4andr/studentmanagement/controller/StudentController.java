package com.github.rama4andr.studentmanagement.controller;

import com.github.rama4andr.studentmanagement.dto.StudentDto;
import com.github.rama4andr.studentmanagement.service.StudentService;
import com.github.rama4andr.studentmanagement.util.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // GET: Получение всех студентов, которые не помечены как удаленные
    @GetMapping("/get_all")
    public ResponseEntity<ResponseMessage<List<StudentDto>>> getAllStudents() {
        List<StudentDto> allStudents = studentService.getAllStudents();
        return ResponseEntity.ok(new ResponseMessage<>("Список студентов получен успешно.", allStudents));
    }

    // POST: Добавление нового студента
    @PostMapping("/add")
    public ResponseEntity<ResponseMessage<StudentDto>> addStudent(@RequestBody StudentDto studentDto) {
        StudentDto addedStudent = studentService.addStudent(studentDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseMessage<>("Студент успешно добавлен.", addedStudent));
    }

    // PUT: Обновление студента по ID
    @PatchMapping("/update/{id}")
    public ResponseEntity<ResponseMessage<StudentDto>> updateStudent(@PathVariable String id,
                                                                     @RequestBody StudentDto studentDto) {
        StudentDto updatedStudent = studentService.updateStudent(id, studentDto);
        return ResponseEntity.ok(new ResponseMessage<>("Информация о студенте обновлена.", updatedStudent));
    }

    // DELETE: Пометка студента как удаленного
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseMessage<String>> deleteStudent(@PathVariable String id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok(new ResponseMessage<>("Студент успешно помечен как удаленный.", id));
    }
}

