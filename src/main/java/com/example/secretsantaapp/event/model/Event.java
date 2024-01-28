package com.example.secretsantaapp.event.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@ToString
@Getter
@Setter
@Document
@NoArgsConstructor
public class Event {
  @Id private String id;
  private String name;
  private String eventUniqueString;
  private double priceLimit;
  private Set<String> participantEMails;
  @DocumentReference(lazy = true)
  private List<SantaPair> santaPairs;

  private Date startedAt;
  private Date createdAt = new Date();

  public Event(String name, double priceLimit, List<SantaPair> santaPairs) {
    this.name = name;
    this.priceLimit = priceLimit;
    this.santaPairs = santaPairs;
  }

  public Event(String name, double priceLimit) {
    this.name = name;
    this.priceLimit = priceLimit;
    this.santaPairs = new ArrayList<>();
  }
}
