package com.order_management_system.auth_service.controller;

import com.order_management_system.auth_service.dto.LoginRequest;
import com.order_management_system.auth_service.model.User;
import com.order_management_system.auth_service.repository.UserRepository;
import com.order_management_system.auth_service.util.JwtUtil;
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

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request){
        User user = userRepository.findByUsername(request.getUsername())
            .orElseThrow(()-> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(),user.getPassword())){
            throw new RuntimeException("Invalid Credentials");
        }

        return jwtUtil.generateToken(request.getUsername());
    }
}
