package com.openclassrooms.starterjwt.services;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;

@SpringBootTest
public class SessionServiceTest {
    @InjectMocks
    SessionService sessionService;
    @Mock
    UserRepository userRepository;
    @Mock
    SessionRepository sessionRepository;

    private Session mockSession;
    private User mockUser1;
    // private User mockUser2;
    private Long sessionId;
    private List<User> mockUsersList;

    @BeforeEach
    void setup() {
        LocalDateTime date = LocalDateTime.now();
        mockUser1 = new User("foo@bar.com", "foo", "bar", "secret12345!", false);
        // mockUser2 = new User("bar@foo.com", "bar", "foo", "secret67890!!", false);
        Teacher mockTeacher = new Teacher(1L, "last_name", "first_name", date, date);
        mockUsersList = Arrays.asList(mockUser1);
        mockSession = new Session(1L, "sessionName", new Date(), "sessionDescription", mockTeacher, mockUsersList, date,
                date);

        sessionId = mockSession.getId();
    }

    @Test
    void should_create_session() {
        when(sessionRepository.save(mockSession)).thenReturn(mockSession);
        Session session = sessionService.create(mockSession);
        assertThat(session).isEqualTo(mockSession);
    }

    @Test
    void should_delete_session() {
        sessionService.delete(sessionId);
        verify(sessionRepository).deleteById(sessionId);
    }

    @Test
    void should_find_all_sessions() {
        List<Session> listSessionExpected = Arrays.asList(mockSession, mockSession);
        when(sessionRepository.findAll()).thenReturn(listSessionExpected);
        List<Session> listSessionsResult = sessionService.findAll();
        assertThat(listSessionsResult).isEqualTo(listSessionExpected);
    }

}
