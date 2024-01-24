package com.example.secretsantaapp.event.dto;

import com.example.secretsantaapp.event.model.Event;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EventDTO {
  private String name;
  private String eventUniqueString;
  private double priceLimit;
  private List<SantaPairDTO> santaPairs;
  private Date startedAt;
  private Date createdAt;

  public static EventDTO convertToDTO(Event event) {
    return new EventDTO(
        event.getName(),
        event.getEventUniqueString(),
        event.getPriceLimit(),
        event.getSantaPairs().stream().map(SantaPairDTO::convertToDTO).toList(),
        event.getStartedAt(),
        event.getCreatedAt());
  }
}
