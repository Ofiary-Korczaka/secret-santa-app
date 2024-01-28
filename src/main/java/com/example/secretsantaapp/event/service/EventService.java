package com.example.secretsantaapp.event.service;

import com.example.secretsantaapp.common.exception.GenericErrorException;
import com.example.secretsantaapp.event.dto.EventCreationDTO;
import com.example.secretsantaapp.event.dto.EventDTO;
import com.example.secretsantaapp.event.dto.SantaPairCreationDTO;
import com.example.secretsantaapp.event.dto.SantaPairDTO;
import com.example.secretsantaapp.event.exception.EventNotFoundException;
import com.example.secretsantaapp.event.exception.handler.EventAlreadyStartedException;
import com.example.secretsantaapp.event.generator.PairGeneratorService;
import com.example.secretsantaapp.event.model.Event;
import com.example.secretsantaapp.event.model.SantaPair;
import com.example.secretsantaapp.event.repository.EventRepository;
import com.example.secretsantaapp.user.User;
import com.example.secretsantaapp.user.UserService;
import com.example.secretsantaapp.user.exception.UserAlreadyExistsException;
import com.example.secretsantaapp.util.CommonUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EventService {
  private final EventRepository eventRepository;
  private final SantaPairService santaPairService;
  private final UserService userService;
  private final PairGeneratorService pairGeneratorService;
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

    if (santaPairCreationDTO.getUserEmail().equals(santaPairCreationDTO.getSecretSantaForEmail())) {
      throw new GenericErrorException("User cannot be his own secret santa");
    }

    boolean userAlreadyInEvent =
        event.getSantaPairs().stream()
            .anyMatch(
                santaPair ->
                    santaPair.getUser().getEmail().equals(santaPairCreationDTO.getUserEmail()));
    if (userAlreadyInEvent) {
      throw new GenericErrorException("User already is a secret santa");
    }

    User user = userService.loadUserByUsername(santaPairCreationDTO.getUserEmail());
    User secretSantaFor =
        userService.loadUserByUsername(santaPairCreationDTO.getSecretSantaForEmail());

    SantaPair santaPair = santaPairService.createSantaPair(user, secretSantaFor);

    event.getSantaPairs().add(santaPair);
    eventRepository.save(event);

    return SantaPairDTO.convertToDTO(santaPair);
  }

  public EventDTO addParticipantToEvent(String eventUniqueId, String email){
    Optional<Event> eventOptional = eventRepository.findByEventUniqueString(eventUniqueId);
    Event event = eventOptional.orElseThrow(EventNotFoundException::new);
    Set<String> participants = event.getParticipantEMails();
    if(!participants.isEmpty() && participants.contains(email)){
      throw new UserAlreadyExistsException("User is already in this event!");
    }
    participants.add(email);
    event.setParticipantEMails(participants);
    eventRepository.save(event);
    return EventDTO.convertToDTO(event);
  }

  public List<SantaPairDTO> startEvent(String eventUniqueId){
    Optional<Event> eventOptional = eventRepository.findByEventUniqueString(eventUniqueId);
    Event event = eventOptional.orElseThrow(EventNotFoundException::new);
    if(event.getStartedAt() != null){
      throw new EventAlreadyStartedException("This event has already been started!");
    }
    var pairs = pairGeneratorService.generatePairs(event.getParticipantEMails());
    //Update event there with pairs and started at;

    return pairs;
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
