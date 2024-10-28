package com.github.rama4andr.studentmanagement.dto;

import java.io.Serializable;

public class StudentDto implements Serializable {

    private String id;

    private String lastName;

    private String firstName;

    private String patronymic;

    private String group;

    private Double averageGrade;

    public StudentDto() {}

    public StudentDto(String lastName, String firstName, String patronymic, String group, Double averageGrade) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.group = group;
        this.averageGrade = averageGrade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(Double averageGrade) {
        this.averageGrade = averageGrade;
    }
}
