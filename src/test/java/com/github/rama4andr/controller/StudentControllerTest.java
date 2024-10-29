package com.github.rama4andr.controller;

import com.github.rama4andr.studentmanagement.controller.StudentController;
import com.github.rama4andr.studentmanagement.dto.StudentDto;
import com.github.rama4andr.studentmanagement.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    private StudentDto studentDto;

    @BeforeEach
    void setUp() {
        studentDto = new StudentDto("qd1qe234crq234r435","Ivanov", "John", "Ivanovich", "4", 4.6);
    }

    @Test
    void testGetAll() throws Exception {
        when(studentService.getAll()).thenReturn(Collections.singletonList(studentDto));

        mockMvc.perform(get("/student/get_all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    void testAdd() throws Exception {
        when(studentService.add(any(StudentDto.class))).thenReturn(studentDto);

        mockMvc.perform(post("/student/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Doe\",\"course\":\"Computer Science\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testUpdate() throws Exception {
        when(studentService.update(eq("1"), any(StudentDto.class))).thenReturn(studentDto);

        mockMvc.perform(patch("/student/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Doe\",\"course\":\"Computer Science\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testDelete() throws Exception {
        doNothing().when(studentService).delete("1");

        mockMvc.perform(delete("/student/delete/1"))
                .andExpect(status().isNoContent());
    }
}
