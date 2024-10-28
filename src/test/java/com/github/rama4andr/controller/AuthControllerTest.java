package com.github.rama4andr.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.github.rama4andr.studentmanagement.controller.AuthController;
import com.github.rama4andr.studentmanagement.dto.UserDto;
import com.github.rama4andr.studentmanagement.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserDto userDto;

    @BeforeEach
    void setUp() {
        userDto = new UserDto("newuser", "securepassword");
    }

    @Test
    void testCreateUser() throws Exception {
        mockMvc.perform(post("/auth/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"newuser\",\"password\":\"securepassword\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Пользователь успешно создан."));
    }

    @Test
    void testGetAccessToken() throws Exception {
        when(userService.getAccessToken(userDto)).thenReturn("your_access_token_here");

        mockMvc.perform(get("/auth/get_token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"newuser\",\"password\":\"securepassword\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Токен доступа получен успешно."))
                .andExpect(jsonPath("$.data").value("your_access_token_here"));
    }
}
