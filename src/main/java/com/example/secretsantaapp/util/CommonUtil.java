package com.example.secretsantaapp.util;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.UUID;

public class CommonUtil {
  private CommonUtil() {}

  public static String generateUniqueString() {
    return UUID.randomUUID().toString();
  }

  public static String getBaseUrl() {
    return ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
  }
}
