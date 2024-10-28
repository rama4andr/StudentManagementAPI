package com.github.rama4andr.studentmanagement.repository;

import com.github.rama4andr.studentmanagement.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserEntity, String> {

    Optional<UserEntity> getUserEntityByLogin(String login);
}
