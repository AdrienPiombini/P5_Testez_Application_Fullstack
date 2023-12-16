package com.openclassrooms.starterjwt.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationProvider;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    UserRepository userRepository;

    private User user;

    @BeforeEach
    void setup() {

        user = new User("foo@bar.com", "bar", "foo", "secret12345!", false);
    }

    @Test
    void testAuthenticateUser() throws Exception {

        UserDetailsImpl userDetailsImpl = new UserDetailsImpl(1L, "fooBar", "bar", "foo", false, "secret12345!");
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("foo@bar.com");
        loginRequest.setPassword("secret12345!");
        String requestBody = objectMapper.writeValueAsString(loginRequest);
        String jwt = "jwt";
        TestingAuthenticationProvider provider = new TestingAuthenticationProvider();
        TestingAuthenticationToken token = new TestingAuthenticationToken(userDetailsImpl, "ROLE_USER");
        Authentication authentication = provider.authenticate(token);

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtUtils.generateJwtToken(authentication)).thenReturn(jwt);
        when(userRepository.findByEmail(userDetailsImpl.getUsername())).thenReturn(Optional.of(user));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/auth/login")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(jwt))
                .andExpect(jsonPath("$.username").value(userDetailsImpl.getUsername()));

    }

    @Test
    void testRegisterUser() throws Exception {

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("foo@bar.com");
        signupRequest.setFirstName("foo");
        signupRequest.setLastName("bar");
        signupRequest.setPassword("secret12345!");

        String requestBody = objectMapper.writeValueAsString(signupRequest);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/auth/register")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value("User registered successfully!"));
    }

    @Test
    void test_register_user_alerady_exist() throws Exception {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("foo@bar.com");
        signupRequest.setFirstName("foo");
        signupRequest.setLastName("bar");
        signupRequest.setPassword("secret12345!");

        String requestBody = objectMapper.writeValueAsString(signupRequest);

        when(userRepository.existsByEmail("foo@bar.com")).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/auth/register")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("Error: Email is already taken!"));

    }
}
