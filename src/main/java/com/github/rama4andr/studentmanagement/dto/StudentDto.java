package com.github.rama4andr.studentmanagement.dto;

import java.io.Serializable;

public record StudentDto(String id, String lastName, String firstName,
                         String patronymic, String group, Double averageGrade) implements Serializable {}
