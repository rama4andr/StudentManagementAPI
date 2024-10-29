package com.github.rama4andr.studentmanagement.service;

import com.github.rama4andr.studentmanagement.dto.UserDto;

public interface UserService {

    UserDto create(UserDto userDto);

    String getAccessToken(UserDto userDto);
}
