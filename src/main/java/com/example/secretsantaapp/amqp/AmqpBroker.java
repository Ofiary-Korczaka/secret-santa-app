package com.example.secretsantaapp.amqp;

import static com.example.secretsantaapp.amqp.AmqpConst.*;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpBroker {
  @Bean
  public Queue mailQueue() {
    return new Queue(MAIL_QUEUE_NAME, true);
  }

  @Bean
  public DirectExchange mailExchange() {
    return new DirectExchange(MAIL_EXCHANGE_NAME, true, false);
  }

  @Bean
  public Binding mailBinding(Queue queue, DirectExchange directExchange) {
    return BindingBuilder.bind(queue).to(directExchange).with(MAIL_REQUEST_ROUTING_KEY_NAME);
  }
}
