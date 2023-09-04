package com.rabbitmq.springbootrabbit.publisher;


import com.rabbitmq.springbootrabbit.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQJsonProducer {


    @Value("${rabbitmq.exchange.name}")
    private String exchange;


    @Value("${rabbitmq.routing.json.key}")
    private String routingJsonKey;


    private RabbitTemplate rabbitTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);


    public RabbitMQJsonProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void sendJsonMessage(User user){

    }


    public void sendMessage(User user){

        LOGGER.info(String.format("Json  sent -> %s", user.toString()));
        rabbitTemplate.convertAndSend(exchange,routingJsonKey,user);

    }
}




---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------



The provided `RabbitMQJsonProducer` class is responsible for sending JSON messages to a RabbitMQ exchange. Similar to the previous example, it serves as a producer component in a RabbitMQ-based messaging system. Let's break down what this producer file does:

1. **Annotations:**
   - `@Service`: This annotation marks the class as a Spring service, indicating that it should be managed as a Spring bean. It is used to create an instance of this class automatically.

2. **Field Declarations (`@Value`):**
   - `@Value("${rabbitmq.exchange.name}")`: This annotation injects the value of the property `"rabbitmq.exchange.name"` from the application properties or configuration. It represents the name of the RabbitMQ exchange to which messages will be sent.
   - `@Value("${rabbitmq.routing.json.key}")`: This annotation injects the value of the property `"rabbitmq.routing.json.key"` from the application properties. It represents the routing key used when sending JSON messages to the exchange.

3. **Logger:**
   - `private static final Logger LOGGER`: This is a logger instance from the SLF4J (Simple Logging Facade for Java) framework. It is used for logging informational messages and is typically used to record events in the application.

4. **Constructor:**
   - `public RabbitMQJsonProducer(RabbitTemplate rabbitTemplate)`: This is a constructor that takes a `RabbitTemplate` as an argument. The `RabbitTemplate` is a Spring component used for sending messages to RabbitMQ. It's automatically injected into the class via constructor-based dependency injection.

5. **`sendMessage` Method:**
   - `public void sendMessage(User user)`: This method sends a JSON message to the RabbitMQ exchange.
   - `rabbitTemplate.convertAndSend(exchange, routingJsonKey, user)`: It uses the `RabbitTemplate` to send a JSON message represented by the `user` object to the RabbitMQ exchange specified by `exchange` using the routing key specified by `routingJsonKey`.

6. **Logging (`LOGGER.info(...)`)**
   - Before sending the JSON message, the producer logs an informational message indicating that a JSON message is being sent, along with the JSON representation of the `user` object.

7. **`sendJsonMessage` Method:**
   - This method is declared but currently empty. It appears that it was intended to send JSON messages, but its implementation is missing.

In summary, this `RabbitMQJsonProducer` class encapsulates the logic for sending JSON messages to a RabbitMQ exchange. It uses Spring's `RabbitTemplate` for message transmission and retrieves the exchange and routing key values from application properties. When the `sendMessage` method is called, it logs a message and sends the provided `User` object as a JSON message to the specified exchange with the given routing key. The `sendJsonMessage` method is declared but not implemented in the provided code.












