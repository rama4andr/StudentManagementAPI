package com.github.rama4andr.studentmanagement.service.impl;

import com.github.rama4andr.studentmanagement.dto.StudentDto;
import com.github.rama4andr.studentmanagement.entity.StudentEntity;
import com.github.rama4andr.studentmanagement.repository.StudentRepository;
import com.github.rama4andr.studentmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<StudentDto> getAllStudents() {
        return studentRepository.findAllByDeletedIsFalse()
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public StudentDto addStudent(StudentDto student) {
        StudentEntity studentEntity = dtoToEntity(student);
        StudentEntity savedEntity = studentRepository.save(studentEntity);
        return entityToDto(savedEntity);
    }

    @Override
    public StudentDto updateStudent(String id, StudentDto student) {
        StudentEntity studentEntity = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        if (student.getFirstName() != null) {
            studentEntity.setFirstName(student.getFirstName());
        }

        if (student.getLastName() != null) {
            studentEntity.setLastName(student.getLastName());
        }

        if (student.getPatronymic() != null) {
            studentEntity.setPatronymic(student.getPatronymic());
        }

        if (student.getGroup() != null) {
            studentEntity.setGroup(student.getGroup());
        }

        if (student.getAverageGrade() != null) {
            studentEntity.setAverageGrade(student.getAverageGrade());
        }

        StudentEntity savedEntity = studentRepository.save(studentEntity);
        return entityToDto(savedEntity);
    }

    @Override
    public void deleteStudent(String id) {
        StudentEntity studentEntity = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        studentEntity.setDeleted(true);
        studentRepository.save(studentEntity);
    }

    private StudentDto entityToDto(StudentEntity studentEntity) {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(studentEntity.getId());
        studentDto.setLastName(studentEntity.getLastName());
        studentDto.setFirstName(studentEntity.getFirstName());
        studentDto.setPatronymic(studentEntity.getPatronymic());
        studentDto.setGroup(studentEntity.getGroup());
        studentDto.setAverageGrade(studentEntity.getAverageGrade());
        return studentDto;
    }

    private StudentEntity dtoToEntity(StudentDto studentDto) {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setLastName(studentDto.getLastName());
        studentEntity.setFirstName(studentDto.getFirstName());
        studentEntity.setPatronymic(studentDto.getPatronymic());
        studentEntity.setGroup(studentDto.getGroup());
        studentEntity.setAverageGrade(studentDto.getAverageGrade());
        return studentEntity;
    }
}
