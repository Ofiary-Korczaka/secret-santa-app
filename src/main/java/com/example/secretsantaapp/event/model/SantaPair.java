package com.example.secretsantaapp.event.model;

import com.example.secretsantaapp.user.User;
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
public class SantaPair {
  public SantaPair(User user, User secretSantaFor) {
    this.user = user;
    this.secretSantaFor = secretSantaFor;
  }

  public SantaPair(Event event, User user, User secretSantaFor) {
    this.user = user;
    this.secretSantaFor = secretSantaFor;
    this.event = event;
  }

  @Id private String id;

  @DocumentReference(lazy = true)
  private Event event;

  @DocumentReference(lazy = true)
  private User user;

  @DocumentReference(lazy = true)
  private User secretSantaFor;

  private String pairHash;
}
