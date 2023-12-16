package com.openclassrooms.starterjwt.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.time.LocalDateTime;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    SessionRepository sessionRepository;

    @MockBean
    UserRepository userRepository;

    private LocalDateTime date;
    private Session session = new Session(1L, "sessionName", new Date(), "description", null,
            null, date, date);
    private SessionDto sessionDto = new SessionDto(1L, "sessionName", new Date(),
            1L, "description", null,
            date, date);;

    @Test
    void create() throws Exception {
        String requestBody = objectMapper.writeValueAsString(sessionDto);

        when(sessionRepository.save(session)).thenReturn(session);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/session")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(sessionDto.getName()))
                .andExpect(jsonPath("$.description").value(sessionDto.getDescription()));
    }

    @Test
    void testFindAll() throws Exception {
        List<Session> listSession = new ArrayList<>();
        listSession.add(session);
        listSession.add(session);

        when(sessionRepository.findAll()).thenReturn(listSession);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/session")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(listSession.size()))
                .andExpect(jsonPath("$[0].id").value(session.getId()))
                .andExpect(jsonPath("$[0].description").value(session.getDescription()));
    }

    @Test
    void testFindById() throws Exception {
        Long id = session.getId();
        when(sessionRepository.findById(id)).thenReturn(Optional.of(session));
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/session/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testFindById_not_found() throws Exception {
        Long fakeId = 1L;
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/session/{id}", fakeId))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindById_bad_request() throws Exception {
        String wrongFormat = "error";
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/session/{id}", wrongFormat))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testNoLongerParticipate() throws Exception {

        Long sessionID = session.getId();
        List<User> userList = new ArrayList<>();
        User user = new User(1L, "email@email.com", "lastName", "firstName", "password",
                false, date, date);
        userList.add(user);
        session.setUsers(userList);

        Long userId = user.getId();

        when(sessionRepository.findById(sessionID)).thenReturn(Optional.of(session));
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/session/{id}/participate/{userId}", sessionID, userId))
                .andExpect(status().isOk());
    }

    @Test
    void testNoLongerParticipate_not_found_execption() throws Exception {
        Long sessionID = session.getId();
        Long userId = 1L;
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/session/{id}/participate/{userId}", sessionID, userId))
                .andExpect(status().isNotFound());
    }

    @Test
    void testNoLongerParticipate_throw_bad_request() throws Exception {
        Long sessionID = session.getId();
        List<User> userList = new ArrayList<>();
        session.setUsers(userList);
        Long userId = 1L;

        when(sessionRepository.findById(sessionID)).thenReturn(Optional.of(session));

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/session/{id}/participate/{userId}", sessionID, userId))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testNoLongerParticipate_throw_number_format_exception() throws Exception {

        Long sessionID = session.getId();
        String userId = "1L";
        when(sessionRepository.findById(sessionID)).thenReturn(Optional.of(session));
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/session/{id}/participate/{userId}", sessionID, userId))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testParticipate() throws Exception {
        Long sessionID = session.getId();
        List<User> userList = new ArrayList<>();
        User user = new User(1L, "email@email.com", "lastName", "firstName", "password",
                false, date, date);
        userList.add(user);
        session.setUsers(userList);

        User user2 = new User(2L, "foo@bar.com", "foo", "bar", "secret",
                false, date, date);
        Long userId = user2.getId();

        when(sessionRepository.findById(sessionID)).thenReturn(Optional.of(session));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user2));
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/session/{id}/participate/{userId}", sessionID, userId))
                .andExpect(status().isOk());
    }

    @Test
    void testParticipate_Not_found_exception() throws Exception {
        Long sessionID = session.getId();
        List<User> userList = new ArrayList<>();
        User user = new User(1L, "email@email.com", "lastName", "firstName", "password",
                false, date, date);
        userList.add(user);
        session.setUsers(userList);

        Long userId = 54L;

        when(sessionRepository.findById(sessionID)).thenReturn(Optional.of(session));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/session/{id}/participate/{userId}", sessionID, userId))
                .andExpect(status().isNotFound());
    }

    @Test
    void testParticipate_already_participate() throws Exception {
        Long sessionID = session.getId();
        List<User> userList = new ArrayList<>();
        User user = new User(1L, "email@email.com", "lastName", "firstName", "password",
                false, date, date);
        userList.add(user);
        session.setUsers(userList);

        Long userId = user.getId();

        when(sessionRepository.findById(sessionID)).thenReturn(Optional.of(session));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/session/{id}/participate/{userId}", sessionID, userId))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testParticipate_number_exception() throws Exception {
        String sessionID = "fakeID";
        Long userId = 1L;
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/session/{id}/participate/{userId}", sessionID, userId))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDelete() throws Exception {
        Long id = session.getId();
        when(sessionRepository.findById(id)).thenReturn(Optional.of(session));

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/session/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete_when_session_is_null() throws Exception {
        Long id = session.getId();
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/session/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDelete_whith_bad_request() throws Exception {
        String requestBody = objectMapper.writeValueAsString(sessionDto);
        String id = "fakeId";
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/session/{id}", id)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdate() throws Exception {
        Long id = session.getId();
        when(sessionRepository.save(session)).thenReturn(session);
        String requestBody = objectMapper.writeValueAsString(sessionDto);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/session/{id}", id)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(sessionDto.getName()))
                .andExpect(jsonPath("$.description").value(sessionDto.getDescription()));
    }

    @Test
    void testUpdate_thow_bad_request() throws Exception {
        String requestBody = objectMapper.writeValueAsString(sessionDto);
        String id = "fakeID";
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/session/{id}", id)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
