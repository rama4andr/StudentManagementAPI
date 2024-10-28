package com.github.rama4andr.studentmanagement.util;

public class ResponseMessage<T> {
    private String message;
    private T data;

    public ResponseMessage(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}

