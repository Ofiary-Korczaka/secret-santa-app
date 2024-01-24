package com.example.secretsantaapp.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class CommonUtil {
  public static final String EMAIL_REGEX = "[a-z0-9._-]+@[a-z0-9.-]+\\.[a-z]{2,3}";

  private CommonUtil() {}

  public static String generateUniqueString() {
    return UUID.randomUUID().toString();
  }

  public static String getBaseUrl() {
    return ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
  }

  public static boolean validateEmail(String email) {
    return email.matches(EMAIL_REGEX);
  }

  public static String generateHashFromString(String str) throws NoSuchAlgorithmException {
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    byte[] hashBytes = digest.digest(str.getBytes(StandardCharsets.UTF_8));

    return new String(hashBytes);
  }
}
