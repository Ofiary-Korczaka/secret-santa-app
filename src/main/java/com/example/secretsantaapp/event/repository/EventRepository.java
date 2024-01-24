package com.example.secretsantaapp.event.repository;

import com.example.secretsantaapp.event.model.Event;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {
  Optional<Event> findByEventUniqueString(String eventUniqueString);
}
