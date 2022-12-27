package com.example.dreamcar.repositories;

import com.example.dreamcar.models.Offer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OfferRepository extends MongoRepository<Offer,String> {
    List<Offer> findByCarComponentName(String name);
    void deleteByCarComponentNameAndUsername(String name, String username);

    Optional<Offer> findByCarComponentNameAndUsername(String name, String name1);
}
