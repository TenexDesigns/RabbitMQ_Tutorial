To send and receive events in a Spring Boot application, you can use Spring's built-in event handling mechanism. Spring provides an `ApplicationEvent` class for creating custom events and an `ApplicationEventPublisher` interface for publishing events. Here's how to send and receive events in Spring Boot:

**1. Create a Custom Event Class:**

First, create a custom event class by extending `ApplicationEvent`. This class represents the event you want to send. For example:

```java
import org.springframework.context.ApplicationEvent;

public class CustomEvent extends ApplicationEvent {

    private String message;

    public CustomEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
```

**2. Create an Event Publisher:**

Next, create a component that will publish events. This component should use the `ApplicationEventPublisher` interface to publish events. For example:

```java
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public EventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publishCustomEvent(String message) {
        CustomEvent customEvent = new CustomEvent(this, message);
        eventPublisher.publishEvent(customEvent);
    }
}
```

In this example, the `publishCustomEvent` method creates an instance of `CustomEvent` and publishes it using the `eventPublisher`.

**3. Create an Event Listener:**

Create a component that listens for and handles the custom event. This component should define a method annotated with `@EventListener` to handle the event. For example:

```java
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EventListenerComponent {

    @EventListener
    public void handleCustomEvent(CustomEvent event) {
        String message = event.getMessage();
        // Handle the custom event, e.g., log it or perform some action.
        System.out.println("Received custom event with message: " + message);
    }
}
```

In this example, the `handleCustomEvent` method is annotated with `@EventListener` and takes a parameter of the custom event type (`CustomEvent`). It will be called whenever a `CustomEvent` is published.

**4. Trigger Event Publishing:**

You can trigger the event publishing in your application logic, such as in a controller method or service. For example:

```java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

    private final EventPublisher eventPublisher;

    public EventController(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @GetMapping("/publish-event")
    public String publishCustomEvent(@RequestParam String message) {
        eventPublisher.publishCustomEvent(message);
        return "Event published: " + message;
    }
}
```

In this example, an HTTP endpoint `/publish-event` is defined to trigger the event publishing when accessed.

**5. Run the Application:**

Run your Spring Boot application, and you can access the `/publish-event` endpoint with a message parameter to publish a custom event. The `handleCustomEvent` method in the `EventListenerComponent` will be invoked when the event is published.

This pattern allows you to decouple different parts of your application by sending and receiving events, making it easier to implement event-driven architectures and asynchronous communication between components.













  
