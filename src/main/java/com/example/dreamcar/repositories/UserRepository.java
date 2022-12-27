package com.example.dreamcar.repositories;

import com.example.dreamcar.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    void deleteByEmail(String email);
}
