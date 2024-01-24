package com.example.secretsantaapp.event.service;

import com.example.secretsantaapp.event.dto.EventCreationDTO;
import com.example.secretsantaapp.event.dto.EventDTO;
import com.example.secretsantaapp.event.dto.SantaPairCreationDTO;
import com.example.secretsantaapp.event.dto.SantaPairDTO;
import com.example.secretsantaapp.event.exception.EventNotFoundException;
import com.example.secretsantaapp.event.model.Event;
import com.example.secretsantaapp.event.model.SantaPair;
import com.example.secretsantaapp.event.repository.EventRepository;
import com.example.secretsantaapp.user.User;
import com.example.secretsantaapp.user.UserService;
import com.example.secretsantaapp.util.CommonUtil;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {
  private final EventRepository eventRepository;
  private final SantaPairService santaPairService;
  private final UserService userService;

  @Autowired
  public EventService(
      EventRepository eventRepository, SantaPairService santaPairService, UserService userService) {
    this.eventRepository = eventRepository;
    this.santaPairService = santaPairService;
    this.userService = userService;
  }

  public Event getEventById(String id) {
    return eventRepository.findById(id).orElse(null);
  }

  public Optional<Event> getEventByEventUniqueId(String eventUniqueId) {
    return eventRepository.findByEventUniqueString(eventUniqueId);
  }

  public EventDTO createEvent(EventCreationDTO eventCreationDTO) {
    Event event = EventCreationDTO.convertToEntity(eventCreationDTO);
    event.setEventUniqueString(CommonUtil.generateUniqueString());
    eventRepository.save(event);
    // ..

    return EventDTO.convertToDTO(event);
  }

  public EventDTO updateEvent(String eventUniqueId, EventDTO eventDTO) {
    Optional<Event> eventOptional = eventRepository.findByEventUniqueString(eventUniqueId);

    Event event = eventOptional.orElseThrow(EventNotFoundException::new);

    event.setName(eventDTO.getName());
    event.setPriceLimit(eventDTO.getPriceLimit());
    eventRepository.save(event);

    return EventDTO.convertToDTO(event);
  }

  public boolean isUserInEvent(String eventUniqueId, String userUsername) {
    Optional<Event> eventOptional = eventRepository.findByEventUniqueString(eventUniqueId);
    Event event = eventOptional.orElseThrow(EventNotFoundException::new);

    return event.getSantaPairs().stream()
        .anyMatch(santaPair -> santaPair.getUser().getUsername().equals(userUsername));
  }

  public SantaPairDTO addSecretSantaPair(
      String eventUniqueId, SantaPairCreationDTO santaPairCreationDTO) {
    Optional<Event> eventOptional = eventRepository.findByEventUniqueString(eventUniqueId);

    Event event = eventOptional.orElseThrow(EventNotFoundException::new);

    User user = userService.loadUserByUsername(santaPairCreationDTO.getUserEmail());
    User secretSantaFor =
        userService.loadUserByUsername(santaPairCreationDTO.getSecretSantaForEmail());

    SantaPair santaPair = santaPairService.createSantaPair(user, secretSantaFor);

    event.getSantaPairs().add(santaPair);
    eventRepository.save(event);

    return SantaPairDTO.convertToDTO(santaPair);
  }

  public List<SantaPairDTO> getSantaPairs(String eventUniqueId) {
    Optional<Event> eventOptional = eventRepository.findByEventUniqueString(eventUniqueId);
    Event event = eventOptional.orElseThrow(EventNotFoundException::new);

    return event.getSantaPairs().stream().map(SantaPairDTO::convertToDTO).toList();
  }

  public void deleteEvent(String eventUniqueId) {
    Optional<Event> eventOptional = eventRepository.findByEventUniqueString(eventUniqueId);
    Event event = eventOptional.orElseThrow(EventNotFoundException::new);

    eventRepository.delete(event);
  }
}
