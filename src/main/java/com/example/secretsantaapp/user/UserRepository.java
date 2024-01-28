package com.example.secretsantaapp.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

  Optional<User> findFirstByEmail(String email);

  Optional<User> findByEmailVerificationToken(String token);
}
