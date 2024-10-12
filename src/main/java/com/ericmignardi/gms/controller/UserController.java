package com.ericmignardi.gms.controller;

import com.ericmignardi.gms.model.User;
import com.ericmignardi.gms.model.UserDTO;
import com.ericmignardi.gms.security.CustomUserDetailsService;
import com.ericmignardi.gms.security.JwtService;
import com.ericmignardi.gms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.create(user);
    }

    @PostMapping("/authenticate")
    public String authenticate(@RequestBody UserDTO userDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userDTO.username(), userDTO.password()
        ));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(customUserDetailsService.loadUserByUsername(userDTO.username()));
        } else {
            throw new UsernameNotFoundException("Invalid credentials");
        }
    }
}
