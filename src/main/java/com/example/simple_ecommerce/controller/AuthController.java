package com.example.simple_ecommerce.controller;

import com.example.simple_ecommerce.Entity.User;
import com.example.simple_ecommerce.repository.UserRepository;
import com.example.simple_ecommerce.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("User Registered Successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user){
        User newDbUser = userRepository.findByUserName(user.getName())
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        if(passwordEncoder.matches(user.getPassword(), newDbUser.getPassword())){
            String token = jwtUtil.generateToken(user.getName());
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.badRequest().body("Invalid Credentials");
    }
}
