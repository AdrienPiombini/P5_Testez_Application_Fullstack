package com.openclassrooms.starterjwt.payload.request;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
public class SignupRequestTest {
    private SignupRequest signupRequest = new SignupRequest();

    @Test
    void testCanEqual() {
        assertThat(signupRequest.canEqual(signupRequest)).isTrue();
    }

    @Test
    void testEquals() {
        assertThat(signupRequest.equals(signupRequest)).isTrue();
    }

    @Test
    void testEqualWhenNull() {
        assertThat(signupRequest.equals(null)).isFalse();
    }

    @Test
    void testEqualWhenNotEqual() {
        SignupRequest anotherSignupRequest = new SignupRequest();
        anotherSignupRequest.setEmail("not the same mail");
        assertThat(signupRequest.equals(anotherSignupRequest)).isFalse();
    }

    @Test
    void testHashCode() {
        int signupRequestHaschode = signupRequest.hashCode();
        assertThat(signupRequestHaschode).isEqualTo(signupRequest.hashCode());
    }

    @Test
    void testToString() {
        signupRequest.setFirstName("foo");
        assertThat(signupRequest.toString().contains("foo")).isTrue();

    }

}
