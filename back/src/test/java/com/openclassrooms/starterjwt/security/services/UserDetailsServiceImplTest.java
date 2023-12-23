package com.openclassrooms.starterjwt.security.services;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;

@ExtendWith(SpringExtension.class)
public class UserDetailsServiceImplTest {
    @InjectMocks
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Mock
    UserRepository userRepository;

    User user = new User(1L, "foo@bar.com", "foo", "bar", "secret12345!", false,
            LocalDateTime.now(),
            LocalDateTime.now());

    UserDetails userDetailsImpl = UserDetailsImpl.builder()
            .id(1L).username("foo@bar.com").lastName("foo").firstName("bar").password("secret12345!").build();

    String email = user.getEmail();

    @Test
    void testLoadUserByUsername() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        assertThat(userDetailsServiceImpl.loadUserByUsername(email).equals(userDetailsImpl));
    }

    @Test
    void testLoadUserByUsernameNotFoundException() {
        when(userRepository.findByEmail(user.getEmail())).thenThrow(NotFoundException.class);
        assertThrows(NotFoundException.class, () -> userDetailsServiceImpl.loadUserByUsername(email));

    }
}
