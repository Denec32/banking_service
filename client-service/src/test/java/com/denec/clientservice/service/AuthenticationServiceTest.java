package com.denec.clientservice.service;

import com.denec.clientservice.model.User;
import com.denec.clientservice.model.request.RegisterUserRequest;
import com.denec.clientservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class AuthenticationServiceTest {
    private static AuthenticationService setUpAuthenticationService() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);

        Mockito.when(passwordEncoder.encode(Mockito.any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Mockito.when(userRepository.save(Mockito.any()))
                .thenAnswer(invocation ->  invocation.getArgument(0));

        return new AuthenticationService(userRepository, passwordEncoder, null);
    }

    private static final AuthenticationService authenticationService = setUpAuthenticationService();

    @Test
    void signUp_throws_when_passwords_dont_match() {
        var incorrectRequest = new RegisterUserRequest("user", "123", "321");

        assertThatExceptionOfType(RuntimeException.class).isThrownBy(
                () -> authenticationService.signUp(incorrectRequest)
        );
    }

    @Test
    void signUp_successful_when_passwords_match() {
        var correctUser = new RegisterUserRequest("user", "123", "123");
        assertThatCode(() -> authenticationService.signUp(correctUser))
                .doesNotThrowAnyException();
    }
}