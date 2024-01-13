package com.example.secretsantaapp.component.mail;

import com.example.secretsantaapp.user.exception.GenericErrorException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class MailNotificationAmqpTemplate {
  private static final Logger LOGGER = LogManager.getLogger(MailNotificationAmqpTemplate.class);

  private String recipientEmail;
  private String messageSubject;
  private String messageBody;
  private MailMessageCategory messageCategory;

  public String toJson() throws GenericErrorException {
    ObjectMapper objectMapper = new ObjectMapper();

    try {
      return objectMapper.writeValueAsString(this);
    } catch (JsonProcessingException jsonProcessingException) {
      LOGGER.error(
          "Error while converting MailNotificationAmqpTemplate to JSON", jsonProcessingException);
      throw new GenericErrorException(jsonProcessingException.getMessage());
    }
  }
}
