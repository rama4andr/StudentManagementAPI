package com.github.rama4andr.studentmanagement.dto;

import java.io.Serializable;

public record UserDto(String login, String password) implements Serializable {}
