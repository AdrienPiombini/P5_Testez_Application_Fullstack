package com.openclassrooms.starterjwt.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.starterjwt.models.User;

@SpringBootTest
@AutoConfigureMockMvc
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    LocalDateTime date = LocalDateTime.now();
    User user = new User(1L, "lastName@firstName.com", "LastName", "FirstName", "Secret134!", false, date,
            date);;
    String email = user.getEmail();

    @BeforeEach
    void setup() {
        userRepository.save(user);
    }

    @Test
    void testExistsByEmail() {
        boolean result = userRepository.existsByEmail(email);
        assertThat(result).isTrue();
    }

    @Test
    void testFindByEmail() {
        Optional<User> userFindByEmail = userRepository.findByEmail(email);
        User result = userFindByEmail.get();
        assertThat(result).isEqualTo(user);
    }
}
