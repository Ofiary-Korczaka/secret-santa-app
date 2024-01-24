package com.example.secretsantaapp.event.dto;

import com.example.secretsantaapp.event.model.SantaPair;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SantaPairDTO {
  private String userUsername;
  private String secretSantaForUsername;

  public static SantaPairDTO convertToDTO(SantaPair santaPair) {
    return new SantaPairDTO(
        santaPair.getUser().getUsername(), santaPair.getSecretSantaFor().getUsername());
  }
}
