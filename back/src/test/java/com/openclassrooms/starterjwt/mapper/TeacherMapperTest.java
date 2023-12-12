package com.openclassrooms.starterjwt.mapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.models.Teacher;

@SpringBootTest
public class TeacherMapperTest {

    @InjectMocks
    TeacherMapperImpl teacherMapperImpl;

    LocalDateTime date = LocalDateTime.now();

    private TeacherDto teacherDto = new TeacherDto(1L, "LastName", "FirstName", date, date);
    private Teacher teacher = new Teacher(1L, "LastName", "FirstName", date, date);
    private List<Teacher> teachersList = new ArrayList<>(2);
    private List<TeacherDto> teachersDtoList = new ArrayList<>(2);
    private Teacher teacherNull;
    private TeacherDto teacherDTONull;
    private List<Teacher> teachersListNull;
    private List<TeacherDto> teachersDTOListNull;

    @BeforeEach
    void setup() {
        teachersList.add(teacher);
        teachersDtoList.add(teacherDto);

    }

    @Test
    void should_return_teacher() {
        Teacher result = teacherMapperImpl.toEntity(teacherDto);
        assertThat(result).isEqualTo(teacher);
        assertThat(teacherMapperImpl.toEntity(teacherDTONull)).isNull();
    }

    @Test
    void should_return_teacher_dto() {
        TeacherDto result = teacherMapperImpl.toDto(teacher);
        assertThat(result).isEqualTo(teacherDto);
        assertThat(teacherMapperImpl.toDto(teacherNull)).isNull();

    }

    @Test
    void should_return_teacher_list() {
        List<Teacher> result = teacherMapperImpl.toEntity(teachersDtoList);
        assertThat(result).isEqualTo(teachersList);
        assertThat(teacherMapperImpl.toEntity(teachersDTOListNull)).isNull();
    }

    @Test
    void should_return_teacherDto_list() {
        List<TeacherDto> result = teacherMapperImpl.toDto(teachersList);
        assertThat(result).isEqualTo(teachersDtoList);
        assertThat(teacherMapperImpl.toDto(teachersListNull)).isNull();

    }

}
