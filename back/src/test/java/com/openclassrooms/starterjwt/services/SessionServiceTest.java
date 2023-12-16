package com.openclassrooms.starterjwt.services;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;

@ExtendWith(SpringExtension.class)
public class SessionServiceTest {
    @InjectMocks
    SessionService sessionService;
    @Mock
    UserRepository userRepository;
    @Mock
    SessionRepository sessionRepository;

    private Session mockSession;
    private Long sessionId;
    private User mockUser;
    private Long userId;
    private ArrayList<User> mockUsersList;

    @BeforeEach
    void setup() {
        LocalDateTime date = LocalDateTime.now();

        mockUser = new User(1L, "foo@bar.com", "foo", "bar", "secret12345!", false, date, date);
        Teacher mockTeacher = new Teacher(1L, "last_name", "first_name", date, date);
        mockUsersList = new ArrayList<>(1);
        mockUsersList.add(mockUser);
        mockSession = new Session(1L, "sessionName", new Date(), "sessionDescription", mockTeacher, mockUsersList, date,
                date);

        sessionId = mockSession.getId();
        userId = mockUser.getId();
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

    @Test
    void should_find_one_session() {
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(mockSession));
        Session expectedSession = sessionService.getById(sessionId);
        assertThat(expectedSession).isEqualTo(mockSession);
    }

    @Test
    void should_throw_with_wrong_id() {
        Long fakeUserId = 9L;
        Long fakeSessionId = 19L;
        assertThrows(NotFoundException.class, () -> sessionService.participate(fakeSessionId, userId));
        assertThrows(NotFoundException.class, () -> sessionService.participate(mockSession.getId(), fakeUserId));
    }

    @Test
    void should_throw_when_user_already_participate() {
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(mockSession));
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        assertThrows(BadRequestException.class, () -> sessionService.participate(sessionId, userId));
    }

    @Test
    void should_add_user_to_session() {
        mockUsersList.clear();
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(mockSession));
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        sessionService.participate(sessionId, userId);

        assertThat(mockSession.getUsers()).isEqualTo(mockUsersList);
        assertThat(mockSession.getUsers().size()).isEqualTo(1);
        verify(sessionRepository).save(mockSession);
    }

    @Test
    void should_throw_with_wrong_Session_ID() {
        Long fakeSessionId = 19L;
        assertThrows(NotFoundException.class, () -> sessionService.noLongerParticipate(fakeSessionId, userId));
    }

    @Test
    void should_throw_when_user_not_participate() {
        mockUsersList.clear();
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(mockSession));
        assertThrows(BadRequestException.class, () -> sessionService.noLongerParticipate(sessionId, userId));
    }

    @Test
    void should_remove_user_of_session() {
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(mockSession));
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        sessionService.noLongerParticipate(sessionId, userId);

        assertThat(mockSession.getUsers().size()).isEqualTo(0);
        verify(sessionRepository).save(mockSession);
    }

}
