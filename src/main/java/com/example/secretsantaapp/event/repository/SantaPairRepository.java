package com.example.secretsantaapp.event.repository;

import com.example.secretsantaapp.event.model.SantaPair;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SantaPairRepository extends MongoRepository<SantaPair, String> {}
