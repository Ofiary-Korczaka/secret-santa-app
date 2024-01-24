package com.example.secretsantaapp.event.exception.handler;

import com.example.secretsantaapp.event.exception.EventNotFoundException;
import com.example.secretsantaapp.event.exception.UserAlreadyInTheEventException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class EventNotFoundExceptionHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler(EventNotFoundException.class)
  public ResponseEntity<Object> handleEventNotFoundException(
      EventNotFoundException ex, WebRequest request) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("message", "Event not found");

    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UserAlreadyInTheEventException.class)
  public ResponseEntity<Object> handleEventNotFoundException(
      UserAlreadyInTheEventException ex, WebRequest request) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("message", "User already in the event");

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }
}
