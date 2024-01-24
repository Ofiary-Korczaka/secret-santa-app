package com.example.secretsantaapp.event;

import com.example.secretsantaapp.event.dto.EventCreationDTO;
import com.example.secretsantaapp.event.dto.EventDTO;
import com.example.secretsantaapp.event.dto.SantaPairCreationDTO;
import com.example.secretsantaapp.event.dto.SantaPairDTO;
import com.example.secretsantaapp.event.exception.EventNotFoundException;
import com.example.secretsantaapp.event.model.Event;
import com.example.secretsantaapp.event.service.EventService;
import com.example.secretsantaapp.user.UserDTO;
import com.example.secretsantaapp.user.UserService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/event")
public class EventController {
  private final EventService eventService;
  private final UserService userService;

  @Autowired
  public EventController(EventService eventService, UserService userService) {
    this.eventService = eventService;
    this.userService = userService;
  }

  @GetMapping("/{eventUniqueId}")
  public ResponseEntity<EventDTO> getEventByUniqueId(@PathVariable String eventUniqueId) {
    Optional<Event> eventOptional = eventService.getEventByEventUniqueId(eventUniqueId);

    Event event = eventOptional.orElseThrow(EventNotFoundException::new);
    return new ResponseEntity<>(EventDTO.convertToDTO(event), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<EventDTO> createEvent(@RequestBody EventCreationDTO eventCreationDTO) {
    return new ResponseEntity<>(eventService.createEvent(eventCreationDTO), HttpStatus.CREATED);
  }

  @PutMapping("/{eventUniqueId}")
  public ResponseEntity<EventDTO> updateEvent(
      @PathVariable String eventUniqueId, @RequestBody EventDTO eventDTO) {
    return new ResponseEntity<>(eventService.updateEvent(eventUniqueId, eventDTO), HttpStatus.OK);
  }

  @DeleteMapping("/{eventUniqueId}")
  public ResponseEntity<EventDTO> deleteEvent(@PathVariable String eventUniqueId) {
    eventService.deleteEvent(eventUniqueId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/{eventUniqueId}/pair")
  public ResponseEntity<List<SantaPairDTO>> getEventSantaPairs(
      @PathVariable String eventUniqueId) {
    List<SantaPairDTO> santaPairDTOList = eventService.getSantaPairs(eventUniqueId);
    return new ResponseEntity<>(santaPairDTOList, HttpStatus.OK);
  }

  @PostMapping("/{eventUniqueId}/pair")
  public ResponseEntity<SantaPairDTO> addEventSantaPair(
      @PathVariable String eventUniqueId, @RequestBody SantaPairCreationDTO santaPairCreationDTO) {

    //TODO: Change BAD_REQUEST response to sth more readable.

    if (santaPairCreationDTO.getUserEmail().equals(santaPairCreationDTO.getSecretSantaForEmail())) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    UserDTO userDTO = userService.getUserByEmail(santaPairCreationDTO.getUserEmail());
    UserDTO secretSantaForDTO =
        userService.getUserByEmail(santaPairCreationDTO.getSecretSantaForEmail());

    if (!userService.isUserEmailVerified(userDTO.getEmail())
        || !userService.isUserEmailVerified(secretSantaForDTO.getEmail())) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    SantaPairDTO santaPairDTO =
        eventService.addSecretSantaPair(eventUniqueId, santaPairCreationDTO);
    return new ResponseEntity<>(santaPairDTO, HttpStatus.OK);
  }

  public ResponseEntity<SantaPairDTO> removeEventSantaPair(
      String eventUniqueId, UserDTO userDTO) {
    return null;
  }
}
