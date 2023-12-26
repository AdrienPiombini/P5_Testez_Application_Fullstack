package com.openclassrooms.starterjwt.models;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class SessionTest {

    @Mock
    User user;
    @Mock
    Teacher teacher = new Teacher();
    private Session session;
    private ArrayList<User> userList = new ArrayList<>();
    private int sessionHashcode;

    @BeforeEach
    public void setUp() {
        LocalDateTime date = LocalDateTime.now();
        session = createSession(10L, "yogaSesssion", "yogaDescription", teacher, userList, date, date);
        sessionHashcode = session.hashCode();
    }

    @Test
    void testToString() {
        assertThat(session.toString().contains("yogaSesssion")).isTrue();
        assertThat(session.toString().contains("yogaDescription")).isTrue();
    }

    @Test
    public void testEquals() {
        assertThat(session.equals(session)).isTrue();
    }

    @Test
    public void testHashCodes() {
        assertThat(session.hashCode()).isEqualTo(sessionHashcode);
    }

    private Session createSession(
            Long id,
            String name,
            String description,
            Teacher teacher,
            ArrayList<User> userList,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        Session session = new Session();
        session.setId(id);
        session.setName(name);
        session.setDate(new Date());
        session.setDescription(description);
        session.setUsers(userList);
        session.setTeacher(teacher);
        session.setCreatedAt(createdAt);
        session.setUpdatedAt(updatedAt);
        return session;
    }
}