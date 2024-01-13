package com.example.secretsantaapp.component.mail;

import lombok.Getter;

@Getter
public enum MailMessageCategory {
  EMAIL_CONFIRMATION("email_confirmation"),
  PASSWORD_RESET("password_reset");

  private final String value;

  MailMessageCategory(String value) {
    this.value = value;
  }
}
