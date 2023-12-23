package com.openclassrooms.starterjwt.security.jwt;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationProvider;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import nl.altindag.log.LogCaptor;
import nl.altindag.log.model.LogEvent;

@SpringBootTest
public class JwtUtilsTest {
    @InjectMocks
    JwtUtils jwtUtils;

    private Authentication authentication;

    private UserDetailsImpl userDetails;
    String jwt;

    LogCaptor logCaptor;
    LogEvent logEvent;

    @BeforeEach
    public void setup() {
        jwtUtils = new JwtUtils();
        ReflectionTestUtils.setField(jwtUtils, "jwtSecret", "openclassrooms");
        ReflectionTestUtils.setField(jwtUtils, "jwtExpirationMs", 3600);

        userDetails = new UserDetailsImpl(
                1L,
                "foobar",
                "foo",
                "bar",
                true,
                "secret12345!");
        TestingAuthenticationProvider provider = new TestingAuthenticationProvider();
        TestingAuthenticationToken token = new TestingAuthenticationToken(userDetails, "ROLE_USER");
        authentication = provider.authenticate(token);

        jwt = jwtUtils.generateJwtToken(authentication);
        logCaptor = LogCaptor.forClass(JwtUtils.class);
    }

    @Test
    void testGenerateJwtToken() {
        assertThat(jwt).isNotNull();
    }

    @Test
    void testGetUserNameFromJwtToken() {
        String userName = jwtUtils.getUserNameFromJwtToken(jwt);
        assertThat(userName).isEqualTo(userDetails.getUsername());
    }

    @Test
    void testValidateJwtTokenIsTrue() {
        assertThat(jwtUtils.validateJwtToken(jwt)).isEqualTo(true);
    }

    @Test
    void testValidateJwtTokenSignatureException() {
        assertThat(jwtUtils.validateJwtToken(jwt + "545")).isEqualTo(false);
        logEvent = logCaptor.getLogEvents().get(0);
        assertThat(logEvent.getMessage()).isEqualTo("Invalid JWT signature: {}");
        // assertThat(logEvent.getLevel()).isEqualTo("ERROR");
    }

    @Test
    void testValidateJwtTokenMalformedJwtException() {
        assertThat(jwtUtils.validateJwtToken("string")).isEqualTo(false); // MalformedJwtException
        logEvent = logCaptor.getLogEvents().get(0);
        assertThat(logEvent.getMessage()).isEqualTo("Invalid JWT token: {}");
    }

    @Test
    void testValidateJwtTokenExpiredJwtException() {
        ReflectionTestUtils.setField(jwtUtils, "jwtExpirationMs", 0);
        String myTokenExpired = jwtUtils.generateJwtToken(authentication);
        assertThat(jwtUtils.validateJwtToken(myTokenExpired)).isEqualTo(false);
        logEvent = logCaptor.getLogEvents().get(0);
        assertThat(logEvent.getMessage()).isEqualTo("JWT token is expired: {}");
    }

    @Test
    void testValidateJwtTokenUnsupportedJwtException() {
        String myUnvalidatedToken = Jwts.builder().setIssuedAt(new Date()).compact();
        assertThat(jwtUtils.validateJwtToken(myUnvalidatedToken)).isEqualTo(false);
        logEvent = logCaptor.getLogEvents().get(0);
        assertThat(logEvent.getMessage()).isEqualTo("JWT token is unsupported: {}");
    }

    @Test
    void testValidateJwtTokenIllegalArgumentException() {
        assertThat(jwtUtils.validateJwtToken("")).isEqualTo(false);
        logEvent = logCaptor.getLogEvents().get(0);
        assertThat(logEvent.getMessage()).isEqualTo("JWT claims string is empty: {}");
    }
}
