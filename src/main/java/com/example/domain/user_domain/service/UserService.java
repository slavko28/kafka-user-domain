package com.example.domain.user_domain.service;

import com.example.domain.user_domain.model.User;

public interface UserService {

    User create(User user);

    User findByLogin(String userLogin);

}
