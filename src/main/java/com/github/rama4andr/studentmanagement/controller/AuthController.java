package com.github.rama4andr.studentmanagement.controller;

import com.github.rama4andr.studentmanagement.dto.UserDto;
import com.github.rama4andr.studentmanagement.service.UserService;
import com.github.rama4andr.studentmanagement.util.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserService userService;

    // POST: Создание нового пользователя
    @PostMapping("/create")
    public ResponseEntity<ResponseMessage<String>> createUser(@RequestBody UserDto userDto) {
        try {
            userService.createUser(userDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseMessage<>("Пользователь успешно создан.", userDto.login()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseMessage<>("Пользователь с таким логином уже существует: ", userDto.login()));
        }
    }

    // POST: Получение access_token
    @PostMapping("/get_token")
    public ResponseEntity<ResponseMessage<String>> getAccessToken(@RequestBody UserDto userDto) {
        String accessToken = userService.getAccessToken(userDto);
        return ResponseEntity.ok(new ResponseMessage<>("Токен доступа получен успешно.", accessToken));
    }
}


