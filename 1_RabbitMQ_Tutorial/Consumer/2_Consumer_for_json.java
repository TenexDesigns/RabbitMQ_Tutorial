package com.rabbitmq.springbootrabbit.consumer;


import com.rabbitmq.springbootrabbit.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQJsonConsumer {




    private static  final Logger LOGGER = LoggerFactory.getLogger(com.rabbitmq.springbootrabbit.consumer.RabbitMQConsumer.class);

    @RabbitListener(queues ={"${rabbitmq.queue.json.name}"})
    public void consumeJsonMessage (User user){

        LOGGER.info(String.format("Received Json message -> %s",user.toString()));

    }







}



----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------




The provided `RabbitMQJsonConsumer` class is a message consumer in a RabbitMQ-based messaging system. It listens for JSON messages on a specific queue and processes them when new JSON messages arrive. Let's break down what this consumer file does:

1. **Annotations:**
   - `@Service`: This annotation marks the class as a Spring service, indicating that it should be managed as a Spring bean. It is used to create an instance of this class automatically.

2. **Logger:**
   - `private static final Logger LOGGER`: This is a logger instance from the SLF4J (Simple Logging Facade for Java) framework. It is used for logging informational messages and is typically used to record events in the application.

3. **`@RabbitListener` Annotation:**
   - `@RabbitListener(queues = {"${rabbitmq.queue.json.name}"})`: This annotation is used to specify the queue from which this consumer should listen for messages. The queue name is obtained from the property `"rabbitmq.queue.json.name"` defined in the application properties or configuration.

4. **`consumeJsonMessage` Method:**
   - `public void consumeJsonMessage(User user)`: This method is annotated with `@RabbitListener`, which means it will be invoked when a JSON message is available in the specified queue.
   - The method takes a `User` parameter `user`, which represents the content of the received JSON message. The `User` class appears to be a custom data transfer object (DTO) representing a user.
   - Inside the method, it logs an informational message indicating that a JSON message has been received, along with the JSON representation of the `user` object.

In summary, this `RabbitMQJsonConsumer` class defines a message consumer that listens for JSON messages on a specific RabbitMQ queue. When a JSON message is received on that queue, the `consumeJsonMessage` method is called, and it logs the received JSON message. This class is part of the consumer side of a message-driven architecture, where it processes JSON messages delivered to the queue by producers and performs any required business logic or actions based on the received JSON messages.






















