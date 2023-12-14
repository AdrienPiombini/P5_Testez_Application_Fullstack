package com.openclassrooms.starterjwt.mapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;

@SpringBootTest
public class SessionMapperTest {
    @InjectMocks
    SessionMapperImpl sessionMapperImpl;

    LocalDateTime date = LocalDateTime.now();
    private User user = new User(1L, "lastName@firstName.com", "LastName", "FirstName", "Secret134!", false, date,
            date);
    private List<User> userList = new ArrayList<>(1);
    private SessionDto sessionDto;
    private Session session;
    private List<Session> sessionList = new ArrayList<>(1);
    private List<SessionDto> sessionDtoList = new ArrayList<>(1);
    private List<Long> userLongsList = new ArrayList<>(1);
    private List<SessionDto> sessionDtoListNull;
    private List<Session> sessionListNull;
    private Session sessionNull;
    private SessionDto sessionDtoNull;

    @BeforeEach
    void setup() {
        userList.add(user);
        session = new Session(1L, "sessionName", new Date(), "description", null,
                null, date, date);
        sessionList.add(session);
        sessionDto = new SessionDto(1L, "sessionName", new Date(),
                null, "description", userLongsList,
                date, date);
        sessionDtoList.add(sessionDto);
    }

    @Test
    void testToDto() {
        SessionDto result = sessionMapperImpl.toDto(session);
        assertThat(result).isEqualTo(sessionDto);
        assertThat(sessionMapperImpl.toDto(sessionNull)).isNull();

    }

    @Test
    void testToEntity() {
        Session result = sessionMapperImpl.toEntity(sessionDto);
        assertThat(result).isEqualTo(session);
        assertThat(sessionMapperImpl.toEntity(sessionDtoNull)).isNull();

    }

    @Test
    void testToDtoList() {
        List<SessionDto> result = sessionMapperImpl.toDto(sessionList);
        assertThat(result).isEqualTo(sessionDtoList);
        assertThat(sessionMapperImpl.toDto(sessionListNull)).isNull();
    }

    @Test
    void testToEntityList() {
        List<Session> result = sessionMapperImpl.toEntity(sessionDtoList);
        assertThat(result).isEqualTo(sessionList);
        assertThat(sessionMapperImpl.toEntity(sessionDtoListNull)).isNull();
    }
}
