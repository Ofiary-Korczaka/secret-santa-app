package com.example.secretsantaapp.event.exception.handler;

public class EventAlreadyStartedException extends RuntimeException {
  public EventAlreadyStartedException() {
    super();
  }

  public EventAlreadyStartedException(String message) {
    super(message);
  }
}
