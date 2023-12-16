package com.openclassrooms.starterjwt.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.TeacherService;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class TeacherControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    TeacherService teacherService;

    private LocalDateTime date = LocalDateTime.now();
    private Teacher teacher = new Teacher(1L, "TeacherName", "TeacherFirstName", date, date);
    private Long teacherId = teacher.getId();
    private List<Teacher> teacherList = new ArrayList<>();

    @Test
    void testFindAll() throws Exception {
        teacherList.add(teacher);
        when(teacherService.findAll()).thenReturn(teacherList);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/teacher"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(teacherId));
    }

    @Test
    void testFindById() throws Exception {

        when(teacherService.findById(teacherId)).thenReturn(teacher);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/teacher/{id}", teacherId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(teacherId));
    }

    @Test
    void testFindById_not_found_exception() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/teacher/{id}", teacherId))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindById_bad_request_exception() throws Exception {
        String teacherId = "1L";

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/teacher/{id}", teacherId))
                .andExpect(status().isBadRequest());
    }
}
