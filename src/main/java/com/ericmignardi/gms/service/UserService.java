package com.ericmignardi.gms.service;

import com.ericmignardi.gms.model.User;
import com.ericmignardi.gms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User create(User user) {
        return userRepository.save(user);
    }
}