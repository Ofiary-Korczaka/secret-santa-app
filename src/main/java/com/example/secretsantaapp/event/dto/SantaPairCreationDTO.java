package com.example.secretsantaapp.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SantaPairCreationDTO {
  String userEmail;
  String secretSantaForEmail;
}
