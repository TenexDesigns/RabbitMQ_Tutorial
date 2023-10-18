To use RabbitMQ for communication between your microservices, you need to perform the following steps:

1. **Setup RabbitMQ**: You can set up RabbitMQ locally or use a Docker container. RabbitMQ is accessible by default over port 15672 once the setup is complete and RabbitMQ is running [Source 5](https://www.springcloud.io/post/2022-03/messaging-using-rabbitmq-in-spring-boot-application/).

2. **Add dependencies**: In your `pom.xml` file, add the Spring Boot starter AMQP dependency:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

3. **Configure RabbitMQ**: Create a configuration class for RabbitMQ. This class should define the `ConnectionFactory`, `RabbitTemplate` and `RabbitAdmin` beans:

```java
@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.host}")
    String host;

    @Value("${spring.rabbitmq.port}")
    int port;

    @Value("${spring.rabbitmq.username}")
    String username;

    @Value("${spring.rabbitmq.password}")
    String password;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(host);
        cachingConnectionFactory.setPort(port);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        return cachingConnectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }
}
```
Remember to add these properties to your `application.properties` file:

```properties
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
```

4. **Publish a message to RabbitMQ**: The `RabbitTemplate` class provides a method called `convertAndSend` to publish messages to a RabbitMQ exchange. In your `ReviewService`, you can use this method to publish a message whenever a new review is added:

```java
@Autowired
private RabbitTemplate rabbitTemplate;

public ReviewModel createReview(ReviewModel review) {
    ReviewModel createdReview = reviewRepository.save(review);
    rabbitTemplate.convertAndSend("exchangeName", "routingKey", createdReview.getBookId());
    return createdReview;
}
```
Here, `exchangeName` is the name of the RabbitMQ exchange to which the message will be published, and `routingKey` is the key that RabbitMQ will use to route the message to the correct queue. Replace these with the actual exchange name and routing key as per your setup.

5. **Consume the message in `BookCatalogueService`**: You can use the `@RabbitListener` annotation to create a method that will be invoked whenever a message arrives in a specific queue:

```java
@RabbitListener(queues = "queueName")
public void updateReviewCount(String bookId) {
    // Increment the review count for the book
}
```
Here, `queueName` is the name of the RabbitMQ queue from which the messages will be consumed.

6. **Error Handling**: Consider implementing error handling for your messaging system. For instance, if the `BookCatalogueService` is down when a new review is added, the message sent by `ReviewService` will be lost. To prevent this, you can configure RabbitMQ to requeue messages that could not be delivered. Also, you might want to configure dead-letter-exchanges for handling messages that could not be processed.

Remember to adjust the exchange, queue, and routing key names according to your actual RabbitMQ setup. Also, ensure that the RabbitMQ server is running and accessible from both your microservices [Source 0](https://www.kindsonthegenius.com/microservices/rabbitmq-in-microservices-step-by-step-tutorial-with-springboot/), [Source 2](https://reflectoring.io/event-messaging-with-spring-boot-and-rabbitmq/), [Source 4](https://progressivecoder.com/microservice-communication-using-spring-cloud-stream-and-rabbitmq/), [Source 5](https://www.springcloud.io/post/2022-03/messaging-using-rabbitmq-in-spring-boot-application/).





..
