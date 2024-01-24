package com.example.secretsantaapp.event.exception;

public class UserAlreadyInTheEventException extends RuntimeException {
  public UserAlreadyInTheEventException() {
    super();
  }

  public UserAlreadyInTheEventException(String message) {
    super(message);
  }
}
