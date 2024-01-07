package com.example.secretsantaapp.amqp;

public class AmqpConst {
  private AmqpConst() {}

  public static final String MAIL_EXCHANGE_NAME = "mail-exchange";
  public static final String MAIL_QUEUE_NAME = "mail-queue";
  public static final String MAIL_REQUEST_ROUTING_KEY_NAME = "mail-request";
}
