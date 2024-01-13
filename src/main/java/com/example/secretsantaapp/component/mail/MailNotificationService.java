package com.example.secretsantaapp.component.mail;

import com.example.secretsantaapp.amqp.AmqpConst;
import com.example.secretsantaapp.user.UserDTO;
import com.example.secretsantaapp.util.CommonUtil;
import java.net.URI;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MailNotificationService {
  private final RabbitTemplate rabbitTemplate;

  public MailNotificationService(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  public void sendEmailConfirmation(UserDTO userDTO, String token) {
    final String template = "Hello %s, please confirm your email by clicking on the link below: %s";

    URI verificationLinkUri =
        URI.create(CommonUtil.getBaseUrl()).resolve("/api/v1/users/verify/" + token);

    String messageContent = String.format(template, userDTO.getName(), verificationLinkUri);

    MailNotificationAmqpTemplate emailContent =
        MailNotificationAmqpTemplate.builder()
            .recipientEmail(userDTO.getEmail())
            .messageSubject("Email confirmation")
            .messageCategory(MailMessageCategory.EMAIL_CONFIRMATION)
            .messageBody(messageContent)
            .build();

    rabbitTemplate.convertAndSend(
        AmqpConst.MAIL_EXCHANGE_NAME,
        AmqpConst.MAIL_REQUEST_ROUTING_KEY_NAME,
        emailContent.toJson());
  }
}
