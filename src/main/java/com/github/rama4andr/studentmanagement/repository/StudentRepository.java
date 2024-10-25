package com.github.rama4andr.studentmanagement.repository;

import com.github.rama4andr.studentmanagement.entity.StudentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StudentRepository extends MongoRepository<StudentEntity, String> {

    List<StudentEntity> findAllByDeletedIsFalse();
}
