package com.example.secretsantaapp.potentialgift;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PotentialGiftRepository extends MongoRepository<PotentialGift, String> {
    Optional<List<PotentialGift>> findPotentialGiftByEmail(String email);
}