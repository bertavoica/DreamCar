package com.example.dreamcar.service;

import com.example.dreamcar.models.User;
import com.example.dreamcar.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
        this.userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email not found"));
    }

}
