package com.openclassrooms.starterjwt.services;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;

@SpringBootTest
public class TeacherServiceTest {

    @InjectMocks
    TeacherService teacherService;
    @Mock
    TeacherRepository teacherRepository;

    Teacher teacherMock = new Teacher(1L, "lastname", "firstname", LocalDateTime.now(), LocalDateTime.now());

    @Test
    void testFindAll() {
        List<Teacher> teacherList = Arrays.asList(teacherMock);
        when(teacherRepository.findAll()).thenReturn(teacherList);
        List<Teacher> result = teacherService.findAll();
        assertThat(result).isEqualTo(teacherList);
    }

    @Test
    void testFindById() {
        Long teacherId = teacherMock.getId();
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacherMock));
        Teacher result = teacherService.findById(teacherId);
        assertThat(result).isEqualTo(teacherMock);
    }
}
