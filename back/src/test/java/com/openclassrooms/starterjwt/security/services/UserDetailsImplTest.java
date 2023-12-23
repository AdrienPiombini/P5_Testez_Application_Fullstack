package com.openclassrooms.starterjwt.security.services;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class UserDetailsImplTest {
    @InjectMocks
    UserDetailsImpl userDetailsImpl;

    @BeforeEach
    void setup() {
        userDetailsImpl = UserDetailsImpl.builder()
                .id(1L)
                .username("foobar")
                .firstName("foo")
                .lastName("bar")
                .password("secret12345!")
                .admin(true)
                .build();
    }

    @Test
    void testBuilder() {
        assertThat(userDetailsImpl.isAccountNonExpired()).isTrue();
    }

    @Test
    void testEqualsFalse() {
        Object object = new Object();
        assertThat(userDetailsImpl.equals(object)).isFalse();
        assertThat(userDetailsImpl.equals(null)).isFalse();
    }

    @Test
    void testEqualsTrue() {
        Object object = userDetailsImpl;
        assertThat(userDetailsImpl.equals(object)).isTrue();
    }

    @Test
    void testGetAdmin() {
        assertThat(userDetailsImpl.getAdmin()).isTrue();

    }

    @Test
    void testGetAuthorities() {
        assertThat(userDetailsImpl.getAuthorities()).isEqualTo(new HashSet<GrantedAuthority>());
    }

    @Test
    void testGetFirstName() {
        assertThat(userDetailsImpl.getFirstName()).isEqualTo("foo");
    }

    @Test
    void testGetId() {
        assertThat(userDetailsImpl.getId()).isEqualTo(1L);

    }

    @Test
    void testGetLastName() {
        assertThat(userDetailsImpl.getLastName()).isEqualTo("bar");
    }

    @Test
    void testGetPassword() {
        assertThat(userDetailsImpl.getPassword()).isEqualTo("secret12345!");
    }

    @Test
    void testGetUsername() {
        assertThat(userDetailsImpl.getUsername()).isEqualTo("foobar");
    }

    @Test
    void testIsAccountNonExpired() {
        assertThat(userDetailsImpl.isAccountNonExpired()).isTrue();
    }

    @Test
    void testIsAccountNonLocked() {
        assertThat(userDetailsImpl.isAccountNonLocked()).isTrue();
    }

    @Test
    void testIsCredentialsNonExpired() {
        assertThat(userDetailsImpl.isCredentialsNonExpired()).isTrue();
    }

    @Test
    void testIsEnabled() {
        assertThat(userDetailsImpl.isEnabled()).isTrue();
    }
}
