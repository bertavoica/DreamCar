package com.example.dreamcar.service;

import com.example.dreamcar.models.User;

public interface UserService {
    void save(User user);
    User findByEmail(String email);
}
