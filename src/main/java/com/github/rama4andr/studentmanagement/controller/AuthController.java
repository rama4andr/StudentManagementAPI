package com.github.rama4andr.studentmanagement.controller;

import com.github.rama4andr.studentmanagement.dto.UserDto;
import com.github.rama4andr.studentmanagement.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // POST: Создание нового пользователя
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody UserDto userDto) {
        try {
            userService.create(userDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(userDto.login());
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(userDto.login());
        }
    }

    // POST: Получение access_token
    @PostMapping()
    public ResponseEntity<String> getAccessToken(@RequestBody UserDto userDto) {
        String accessToken = userService.getAccessToken(userDto);
        return ResponseEntity.status(HttpStatus.OK).body(accessToken);
    }
}


