package com.github.rama4andr.studentmanagement.service.impl;

import com.github.rama4andr.studentmanagement.dto.StudentDto;
import com.github.rama4andr.studentmanagement.entity.StudentEntity;
import com.github.rama4andr.studentmanagement.repository.StudentRepository;
import com.github.rama4andr.studentmanagement.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<StudentDto> getAll() {
        return studentRepository.findAllByDeletedIsFalse()
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public StudentDto add(StudentDto student) {
        StudentEntity studentEntity = dtoToEntity(student);
        StudentEntity savedEntity = studentRepository.save(studentEntity);
        return entityToDto(savedEntity);
    }

    @Override
    public StudentDto update(String id, StudentDto student) {
        StudentEntity studentEntity = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student with this ID does not exist: " + id));

        if (student.firstName() != null) {
            studentEntity.setFirstName(student.firstName());
        }

        if (student.lastName() != null) {
            studentEntity.setLastName(student.lastName());
        }

        if (student.patronymic() != null) {
            studentEntity.setPatronymic(student.patronymic());
        }

        if (student.group() != null) {
            studentEntity.setGroup(student.group());
        }

        if (student.averageGrade() != null) {
            studentEntity.setAverageGrade(student.averageGrade());
        }

        StudentEntity savedEntity = studentRepository.save(studentEntity);
        return entityToDto(savedEntity);
    }

    @Override
    public void delete(String id) {
        StudentEntity studentEntity = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student with this ID does not exist: " + id));
        studentEntity.setDeleted(true);
        studentRepository.save(studentEntity);
    }

    private StudentDto entityToDto(StudentEntity studentEntity) {
        return new StudentDto(studentEntity.getId(), studentEntity.getLastName(), studentEntity.getFirstName(),
        studentEntity.getPatronymic(), studentEntity.getGroup(), studentEntity.getAverageGrade());
    }

    private StudentEntity dtoToEntity(StudentDto studentDto) {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setLastName(studentDto.lastName());
        studentEntity.setFirstName(studentDto.firstName());
        studentEntity.setPatronymic(studentDto.patronymic());
        studentEntity.setGroup(studentDto.group());
        studentEntity.setAverageGrade(studentDto.averageGrade());
        return studentEntity;
    }
}
