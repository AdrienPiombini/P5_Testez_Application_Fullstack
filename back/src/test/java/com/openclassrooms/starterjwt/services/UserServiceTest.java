package com.openclassrooms.starterjwt.services;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService;
    @Mock
    UserRepository userRepository;

    private User mockUser;
    private Long userId;

    @BeforeEach
    void setup() {
        mockUser = new User(
                1L,
                "foo@bar.com",
                "foo",
                "bar",
                "secret123",
                false,
                LocalDateTime.now(),
                LocalDateTime.now());

        userId = mockUser.getId();
    }

    @Test
    void should_delete_user() {
        userService.delete(userId);
        verify(userRepository).deleteById(userId);
    }

    @Test
    void should_find_user_byId() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        User user = userService.findById(mockUser.getId());
        assertThat(user).isEqualTo(mockUser);
    }
}
