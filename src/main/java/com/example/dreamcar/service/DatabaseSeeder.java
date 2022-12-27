package com.example.dreamcar.service;

import com.example.dreamcar.models.CarComponent;
import com.example.dreamcar.models.User;
import com.example.dreamcar.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @Override
    public void run(String... args) throws Exception {

        if (!userRepository.findByEmail("berta_ioana.voica@stud.pub.etti.ro").isPresent()) {
            userService.save(new User(
                    "bertaioana",
                    "berta_ioana.voica@stud.pub.etti.ro",
                    "test1234",
                    "Berta",
                    "Voica",
                    "0753467440",
                    "Address 1",
                    "BUYER"
                    ));
        }

        if (!userRepository.findByEmail("voica.berta@yahoo.com").isPresent()) {
            userService.save(new User(
                    "bertaioana",
                    "voica.berta@yahoo.com",
                    "test1234",
                    "Berta",
                    "Voica",
                    "0753467440",
                    "Address 1",
                    "SUPPLIER"
            ));
        }

        if (!userRepository.findByEmail("bertavoica@gmail.com").isPresent()) {
            userService.save(new User(
                    "bertaioana",
                    "bertavoica@gmail.com",
                    "test1234",
                    "Berta",
                    "Voica",
                    "0753467440",
                    "Address 1",
                    "SUPPLIER"
            ));
        }
    }
}
