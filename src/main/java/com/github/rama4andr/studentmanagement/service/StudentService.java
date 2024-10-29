package com.github.rama4andr.studentmanagement.service;

import com.github.rama4andr.studentmanagement.dto.StudentDto;

import java.util.List;

public interface StudentService {

    List<StudentDto> getAll();

    StudentDto add(StudentDto student);

    StudentDto update(String id, StudentDto student);

    void delete(String id);
}
