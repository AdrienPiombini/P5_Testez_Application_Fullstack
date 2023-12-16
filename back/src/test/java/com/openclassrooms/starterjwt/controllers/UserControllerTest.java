package com.openclassrooms.starterjwt.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "foo@bar.com")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    UserService userService;

    private LocalDateTime date = LocalDateTime.now();
    private User user = new User(1L, "foo@bar.com", "foo", "bar", "secret12345!", false, date, date);
    private Long userId = user.getId();

    @Test
    void testFindById() throws Exception {
        when(userService.findById(userId)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/user/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId));
    }

    @Test
    void testFindById_not_found_exception() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/user/{id}", userId))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindById_bad_request_exception() throws Exception {
        String userId = "1L";
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/user/{id}", userId))
                .andExpect(status().isBadRequest());

    }

    @Test
    void testSave() throws Exception {
        when(userService.findById(userId)).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/user/{id}", userId))
                .andExpect(status().isOk());
    }

    @Test
    void testSave_not_found_exception() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/user/{id}", userId))
                .andExpect(status().isNotFound());
    }

    @Test
    void testSave_bad_request_exception() throws Exception {
        String userId = "1L";
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/user/{id}", userId))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSave_unauthorized_exception() throws Exception {
        user.setEmail("different@email.com");
        when(userService.findById(userId)).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/user/{id}", userId))
                .andExpect(status().isUnauthorized());
    }
}
