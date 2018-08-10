package com.example.domain.user_domain.service.impl;

import com.example.domain.user_domain.model.User;
import com.example.domain.user_domain.repository.UserRepository;
import com.example.domain.user_domain.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        log.info("Creating new User:\n{}", user);
        return userRepository.save(user);
    }

    @Override
    public User findByLogin(String userLogin) {
        log.info("Find user by login: \'{}\'", userLogin);
        return userRepository.findByLogin(userLogin)
                .orElseThrow(() -> new IllegalArgumentException(String.format("User not found by login: '%s'", userLogin)));
    }
}
