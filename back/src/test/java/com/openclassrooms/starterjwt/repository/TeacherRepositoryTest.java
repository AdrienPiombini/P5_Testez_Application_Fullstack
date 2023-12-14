// package com.openclassrooms.starterjwt.repository;

// import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

// import java.time.LocalDateTime;
// import java.util.Optional;

// import org.junit.jupiter.api.Test;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// import com.openclassrooms.starterjwt.models.Teacher;

// @DataJpaTest
// @EnableJpaRepositories(basePackageClasses = TeacherRepository.class)
// public class TeacherRepositoryTest {
// @Autowired
// TeacherRepository teacherRepository;

// Teacher teacherMock = new Teacher(1L, "lastname", "firstname",
// LocalDateTime.now(), LocalDateTime.now());

// @Test
// void should_find_by_id() {
// teacherRepository.save(teacherMock);
// Optional<Teacher> result = teacherRepository.findById(1L);
// assertThat(result).isEqualTo(teacherMock);

// }
// }
