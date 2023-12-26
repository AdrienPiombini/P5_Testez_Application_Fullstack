package com.openclassrooms.starterjwt.models;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDateTime;

import org.junit.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTest {

    @Mock
    User user = createUser(1L, "foo@bar.com", "foo", "bar", "Secret12345!", false, LocalDateTime.now(),
            LocalDateTime.now());
    int userHashCode = user.hashCode();

    @Test
    public void testHashCodes() {
        assertThat(userHashCode).isEqualTo(user.hashCode());
    }

    @Test
    public void testEquals() {
        assertThat(user.equals(user)).isTrue();
    }

    @Test
    public void testEqualsWhenIsNull() {
        assertThat(user.equals(null)).isFalse();
    }

    private User createUser(
            Long id,
            String email,
            String lastName,
            String firstName,
            String password,
            Boolean isAdmin,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setLastName(lastName);
        user.setFirstName(firstName);
        user.setPassword(password);
        user.setAdmin(isAdmin);
        user.setCreatedAt(createdAt);
        user.setUpdatedAt(updatedAt);
        return user;
    }
}
