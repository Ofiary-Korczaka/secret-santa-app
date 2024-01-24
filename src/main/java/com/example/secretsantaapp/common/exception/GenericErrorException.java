package com.example.secretsantaapp.common.exception;

public class GenericErrorException extends RuntimeException {
  public GenericErrorException(String message) {
    super(message);
  }

  public GenericErrorException(Throwable cause) {
    super(cause);
  }
}
