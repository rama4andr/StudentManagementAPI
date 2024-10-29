package com.github.rama4andr.studentmanagement.util;

public record ResponseMessage<T>(String message, T data) {}