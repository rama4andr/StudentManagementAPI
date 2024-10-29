package com.github.rama4andr.studentmanagement.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Setter
@Getter
@Document(collection = "student")
public class StudentEntity {

    @Id
    private String id;

    @Size(max = 255)
    @NotNull
    @Field("last_name")
    private String lastName;

    @Size(max = 255)
    @NotNull
    @Field("first_name")
    private String firstName;

    @Size(max = 255)
    @Field("patronymic")
    private String patronymic;

    @Size(max = 255)
    @NotNull
    @Field("group")
    private String group;

    @NotNull
    @Field("average_grade")
    private Double averageGrade;

    @Field("deleted")
    private boolean deleted = false;
}
