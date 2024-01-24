package com.example.secretsantaapp.event.dto;

import com.example.secretsantaapp.event.model.Event;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventCreationDTO {
  private String name;
  private double priceLimit;

  public static Event convertToEntity(EventCreationDTO eventCreationDTO) {
    return new Event(eventCreationDTO.getName(), eventCreationDTO.getPriceLimit());
  }
}
