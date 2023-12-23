package com.openclassrooms.starterjwt.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDateTime;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.starterjwt.models.Teacher;

@SpringBootTest
@AutoConfigureMockMvc
public class TeacherRepositoryTest {
    @Autowired
    TeacherRepository teacherRepository;

    LocalDateTime date = LocalDateTime.now();
    Teacher teacher = new Teacher(1L, "lastName", "firstName", date, date);
    Long id = teacher.getId();

    @BeforeEach
    void setup() {
        teacherRepository.save(teacher);
    }

    @Test
    void should_find_by_id() {
        Optional<Teacher> result = teacherRepository.findById(id);
        assertThat(result.get().getLastName()).isEqualTo(teacher.getLastName());
    }

    @Test
    void should_findAll() {
        List<Teacher> result = teacherRepository.findAll();
        assertThat(result.get(0).getLastName()).isEqualTo(teacher.getLastName());
    }
}
