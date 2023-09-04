package com.rabbitmq.springbootrabbit.controller;

import com.rabbitmq.springbootrabbit.dto.User;
import com.rabbitmq.springbootrabbit.publisher.RabbitMQProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
public class MessageJsonController  {

    private RabbitMQProducer jsonProducer;

    public MessageJsonController (RabbitMQProducer jsonProducer){
        this.jsonProducer = jsonProducer;
    }


    @PostMapping("/publish")
    public ResponseEntity<String> sendJsonMessage (@RequestBody  User user){
        jsonProducer.sendMessage(String.valueOf(user));
        return ResponseEntity.ok(" Json message sent to RabbitMQ......");
    }
}















---------------------------------------------------------------------------------------------------------------------------------------------------------------------

The provided `MessageJsonController` class is a Spring MVC controller responsible for handling HTTP requests related to sending JSON messages to a RabbitMQ exchange. Let's break down what this controller file does:

1. **Annotations:**
   - `@RestController`: This annotation marks the class as a Spring REST controller. It indicates that this class will handle HTTP requests and produce HTTP responses.

   - `@RequestMapping("/api/v1")`: This annotation specifies the base URL path for all the endpoints defined in this controller. All the endpoints in this controller will be relative to the `/api/v1` base path.

2. **Constructor Injection:**
   - `public MessageJsonController(RabbitMQProducer jsonProducer)`: This is a constructor that takes a `RabbitMQProducer` as an argument. The `RabbitMQProducer` is injected into the controller via constructor-based dependency injection. This allows the controller to use the `RabbitMQProducer` to send JSON messages to RabbitMQ.

3. **Endpoint Definition:**
   - `@PostMapping("/publish")`: This annotation defines an HTTP POST endpoint with the path `/api/v1/publish`. When a POST request is made to this endpoint, the `sendJsonMessage` method will be invoked.

4. **`sendJsonMessage` Method:**
   - `public ResponseEntity<String> sendJsonMessage(@RequestBody User user)`: This method handles POST requests to the `/publish` endpoint.
   - It takes a request body `user` of type `User`, which represents the JSON message to be sent to RabbitMQ. The `User` class appears to be a custom data transfer object (DTO) representing a user.

   - Inside the method, it calls `jsonProducer.sendMessage(String.valueOf(user));` to send the JSON message to RabbitMQ using the `RabbitMQProducer`. The `String.valueOf(user)` converts the `User` object to its JSON representation before sending it to RabbitMQ.

   - Finally, it returns an HTTP response with a status code of 200 (OK) and a response body of "Json message sent to RabbitMQ......". This indicates a successful message publication.

In summary, this `MessageJsonController` class defines an HTTP endpoint `/api/v1/publish` that allows clients to send JSON messages to a RabbitMQ exchange. When a POST request is made to this endpoint with a JSON payload representing a `User` object, it invokes the `sendJsonMessage` method, which uses the injected `RabbitMQProducer` to publish the JSON message to RabbitMQ. The controller then responds with a success message. This setup allows external clients to send JSON messages to RabbitMQ by making POST requests to this endpoint.



