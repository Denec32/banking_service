package com.denec.clientservice.controller;

import com.denec.clientservice.model.User;
import com.denec.clientservice.model.request.LoginUserRequest;
import com.denec.clientservice.model.request.RegisterUserRequest;
import com.denec.clientservice.service.AuthenticationService;
import com.denec.clientservice.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @PostMapping("/signup")
    public String register(@RequestBody RegisterUserRequest request) {
        User user = authenticationService.signUp(request);
        return jwtService.generateToken(user);
    }

    @PostMapping("/login")
    public String authenticate(@RequestBody LoginUserRequest request) {
        User authenticatedUser = authenticationService.signIn(request);

        return jwtService.generateToken(authenticatedUser);
    }
}