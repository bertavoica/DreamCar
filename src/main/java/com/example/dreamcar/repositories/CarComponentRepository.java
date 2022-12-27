package com.example.dreamcar.repositories;

import com.example.dreamcar.models.CarComponent;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CarComponentRepository extends MongoRepository<CarComponent,String> {
    Optional<CarComponent> findByName(String name);
    List<CarComponent> findByNameContaining(String name);

    void deleteByName(String name);
}
