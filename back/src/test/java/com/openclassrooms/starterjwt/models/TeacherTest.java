package com.openclassrooms.starterjwt.models;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import java.time.LocalDateTime;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TeacherTest {
    @Mock
    Teacher teacher = createTeacher(1L, "foo", "bar", LocalDateTime.now(), LocalDateTime.now());
    int teacherHashCode = teacher.hashCode();

    @Test
    public void testHashCodes() {
        assertThat(teacherHashCode).isEqualTo(teacher.hashCode());
    }

    @Test
    public void testToString() {
        assertThat(teacher.toString().contains("foo")).isTrue();
    }

    @Test
    public void testEquals() {
        assertThat(teacher.equals(teacher)).isTrue();
    }

    @Test
    public void testEqualWhenIsNull() {
        assertThat(teacher.equals(null)).isFalse();
    }

    private Teacher createTeacher(
            Long id,
            String lastName,
            String firstName,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        Teacher teacher = new Teacher();
        teacher.setId(id);
        teacher.setLastName(lastName);
        teacher.setFirstName(firstName);
        teacher.setCreatedAt(createdAt);
        teacher.setUpdatedAt(updatedAt);
        return teacher;
    }
}
