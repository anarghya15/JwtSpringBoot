package com.example.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo-controller")
@RequiredArgsConstructor
public class DemoController {

    @GetMapping("/hello1")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello from secured endpoint - USER");
    }

    @GetMapping("/hello2")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> sayHello2(){
        return ResponseEntity.ok("Hello from secured endpoint - ADMIN");
    }
}
