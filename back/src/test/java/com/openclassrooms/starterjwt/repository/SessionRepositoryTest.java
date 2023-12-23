package com.openclassrooms.starterjwt.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;

@SpringBootTest
@AutoConfigureMockMvc
public class SessionRepositoryTest {

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TeacherRepository teacherRepository;

    LocalDateTime date = LocalDateTime.now();
    private List<User> userList = new ArrayList<>();
    private Teacher teacher = new Teacher(1L, "foo", "bar", date, date);
    private Session session = new Session(1L, "sessionName", new Date(), "description", teacher,
            userList, date, date);

    private Long id = session.getId();

    @BeforeEach
    void setup() {
        teacherRepository.save(teacher);
        sessionRepository.save(session);
    }

    @Test
    void findById() {
        Optional<Session> result = sessionRepository.findById(id);
        assertThat(result.get()).isEqualTo(session);
    }

    @Test
    void findAll() {
        List<Session> result = sessionRepository.findAll();
        assertThat(result).isNotNull();
    }

    @Test
    void save() {
        int numberOfSessionInDB = sessionRepository.findAll().size();
        Session newSession = session = Session.builder()
                .name("newSession")
                .date(new Date())
                .description("description")
                .teacher(teacher)
                .users(userList)
                .createdAt(date)
                .updatedAt(date)
                .build();

        sessionRepository.save(newSession);
        int newNumberOfSessionInDB = sessionRepository.findAll().size();
        assertThat(numberOfSessionInDB + 1).isEqualTo(newNumberOfSessionInDB);
    }

    // save
}
