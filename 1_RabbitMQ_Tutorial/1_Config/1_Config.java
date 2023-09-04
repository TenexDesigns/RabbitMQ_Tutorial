package com.rabbitmq.springbootrabbit.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Exchange;


@Configuration
public class RabbitMQConfig {





    //
    @Value("${rabbitmq.queue.name}")
    private String queue;

    @Value("${rabbitmq.queue.json.name}")
    private String jsonQueue;


    @Value("${rabbitmq.exchange.name}")
    private String exchange;


    @Value("${rabbitmq.routing.key}")
    private String routingKey;


    @Value("${rabbitmq.routing.json.key}")
    private String routingJsonKey;



    // spring bean  for queue (store json messages)
    @Bean
    public Queue jsonQueue(){
        return new Queue(jsonQueue);
    }








    // spring bean for rabbitmq queue
    @Bean
    public Queue queue(){

        return new Queue(queue);
    }



    // spring bean from rabbitmq exchange
    @Bean
    public TopicExchange exchange(){

        return  new TopicExchange(exchange);

    }

    // binding between queue and exchange using routing key
    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue())
                .to(exchange())
                .with(routingKey);
    }

    // binding between json queue and exchage using routing key
    @Bean
    public Binding jsonBuilder(){

        return BindingBuilder
                .bind(jsonQueue())
                .to(exchange())
                .with(routingKey);

    }


    public MessageConverter converter(){

        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }





//These three are autoconfigured by spring autoconfiguration
    //connectingFactory
    // RabbitTemplate
    // RabbitAdmin
}









---------------------------------------------------------------------------------------------------------------------------------------------------------------------

  The provided `RabbitMQConfig` class is a configuration class used in a Spring application to set up and configure RabbitMQ messaging components. It plays a crucial role in defining the configuration for RabbitMQ, including the declaration of queues, exchanges, bindings, and message converters. Let's break down what this configuration does and explain its role in RabbitMQ architecture:

1. **Configuration Annotation (`@Configuration`):** This annotation indicates that the class contains configuration methods for the Spring application context. In this case, it configures RabbitMQ components.

2. **Field Declarations for Configuration Properties:** The class declares several fields annotated with `@Value`, which are used to inject values from property files. These fields hold configuration properties like queue names, exchange names, and routing keys.

3. **Queue Configuration (`@Bean` methods):** The class defines two queues using the `@Bean` annotation. These queues represent the destinations where messages will be sent or received. One queue is for plain text messages (`queue()`) and the other for JSON messages (`jsonQueue()`).

4. **Exchange Configuration (`@Bean` method):** The class configures a `TopicExchange` using the `@Bean` annotation. Exchanges are routing mechanisms that distribute messages to queues based on the routing key. A `TopicExchange` is a type of exchange that allows for more flexible message routing using wildcard patterns.

5. **Binding Configuration (`@Bean` methods):** The class defines two binding configurations using `@Bean` methods (`binding()` and `jsonBuilder()`). Bindings connect queues to exchanges and specify the routing key for message distribution.

6. **Message Converter (`converter()` method):** The `converter()` method configures a `Jackson2JsonMessageConverter`. This message converter is responsible for serializing and deserializing messages to/from JSON format. It ensures that messages sent to the queues are properly formatted.

7. **RabbitTemplate Configuration (`amqpTemplate()` method):** The `amqpTemplate()` method creates a `RabbitTemplate` instance, which is used for sending and receiving messages to/from RabbitMQ. It sets the message converter to the previously configured `Jackson2JsonMessageConverter`.

8. **Autoconfigured Beans (ConnectionFactory, RabbitTemplate, RabbitAdmin):** The comments mention that some components (ConnectionFactory, RabbitTemplate, and RabbitAdmin) are autoconfigured by Spring's autoconfiguration. This means that Spring Boot automatically configures these essential RabbitMQ components based on properties in your application.properties or application.yml file.

**RabbitMQ Architecture Overview:**

- **Exchange:** Exchanges are responsible for receiving messages from producers and routing them to one or more queues based on the message's routing key. Different types of exchanges (e.g., direct, fanout, topic) determine how messages are distributed.

- **Queue:** Queues store messages until they are consumed by consumers. Each queue can have one or more consumers listening for messages.

- **Binding:** Bindings define the relationship between exchanges and queues. They specify which queues are subscribed to which exchanges and with what routing key.

- **Message Converter:** Message converters are responsible for serializing and deserializing messages. In this case, the Jackson2JsonMessageConverter converts messages between JSON and Java objects.

- **RabbitTemplate:** The RabbitTemplate is a high-level interface for interacting with RabbitMQ. It simplifies the process of sending and receiving messages.

- **ConnectionFactory:** The ConnectionFactory manages connections to the RabbitMQ server. It is responsible for creating and managing connections, which are used for communication between applications and the RabbitMQ broker.

- **RabbitAdmin:** The RabbitAdmin is used for declaring queues, exchanges, and bindings programmatically. In this configuration, it's autoconfigured by Spring Boot based on your queue and exchange declarations.

In summary, the provided `RabbitMQConfig` class sets up the necessary RabbitMQ components and configurations for a Spring application, allowing it to send and receive messages via RabbitMQ in a structured and organized manner. It aligns with RabbitMQ's messaging architecture, where messages are produced by publishers (producers) and consumed by subscribers (consumers) through exchanges and queues.
