package com.github.rama4andr.studentmanagement.service;

import com.github.rama4andr.studentmanagement.dto.StudentDto;

import java.util.List;

public interface StudentService {

    List<StudentDto> getAllStudents();

    StudentDto addStudent(StudentDto student);

    StudentDto updateStudent(String id, StudentDto student);

    void deleteStudent(String id);
}
