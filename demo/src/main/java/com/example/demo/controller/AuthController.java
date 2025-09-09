package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  // ✅ Register user
  @PostMapping("/register")
  public ResponseEntity<User> register(@RequestBody User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return ResponseEntity.ok(userRepository.save(user));
  }

  // ✅ Login user
  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody User user) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    return ResponseEntity.ok("Login successful for user: " + user.getUsername());
  }
}
