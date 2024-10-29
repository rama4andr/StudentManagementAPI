package com.github.rama4andr.studentmanagement.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "user")
public class UserEntity {

    @Id
    private String id;

    @Setter
    @Getter
    @NotNull
    @Size(max = 255)
    @Field("login")
    private String login;

    @Setter
    @Getter
    @NotNull
    @Size(max = 255)
    @Field("password")
    private String password;

}
