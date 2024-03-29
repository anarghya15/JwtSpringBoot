package com.example.security.service;

import com.example.security.dto.AuthenticationRequest;
import com.example.security.dto.AuthenticationResponse;
import com.example.security.dto.RegisterRequest;
import com.example.security.repository.UserRepository;
import com.example.security.entity.Role;
import com.example.security.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    //private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    //private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail());
                //.password(passwordEncoder.encode(request.getPassword()));
        if(request.getRole().equalsIgnoreCase("user"))
            user.role(Role.USER);
        else if(request.getRole().equalsIgnoreCase("admin"))
            user.role(Role.ADMIN);

        User newUser = user.build();
        repository.save(newUser);

        String role = newUser.getRole().toString();
        Map<String, Object> claims = new HashMap<>();
        claims.put("Role", role);

        var jwtToken = jwtService.generateToken(claims, newUser);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        System.out.println("Checking for username");
        //authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        //User is authenticated
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        System.out.println("Valid username");
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
