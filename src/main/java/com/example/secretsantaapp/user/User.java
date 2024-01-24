package com.example.secretsantaapp.user;

import com.example.secretsantaapp.event.model.Event;
import java.util.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@ToString
@Getter
@Setter
@Document
public class User implements UserDetails {

  @Id private String id;
  private String name;
  private String email;
  private String password;

  private String emailVerificationToken;

  private Date emailVerified;

  @DocumentReference(lazy = true)
  private List<Event> events;

  private Date createdAt = new Date();

  public User(String name, String password, String email) {
    this.name = Objects.requireNonNull(name);
    this.password = password;
    this.email = Objects.requireNonNull(email);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
