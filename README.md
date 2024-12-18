# **Email Kafka Microservice**

The **Email Kafka Microservice** is a robust, event-driven application designed to handle real-time processing of letter and package eligibility validations. By leveraging **Apache Kafka** for messaging and **Reactive Programming** for non-blocking stream processing, the service achieves high performance, scalability, and fault tolerance.

This microservice plays a crucial role in a larger microservices ecosystem by validating letter and package events and generating notifications based on eligibility. It exemplifies a **clean code, pragmatic approach**, adhering to industry best practices such as **DRY**, **SOLID**, and **Separation of Concerns**, ensuring the codebase is maintainable, scalable, and extendable.

---

## **Key Features**

- **Reactive Programming**:
    - Implements event-driven logic using Project Reactor.
    - Non-blocking stream processing for scalability.
- **Kafka Integration**:
    - Listens to Kafka topics for **`LetterEvent`** and **`PackageEvent`**.
    - Publishes **`NotificationEvent`** to notify other services.
- **Eligibility Validation**:
    - Validates data from letters and packages and processes eligibility.
- **Cross-Service Interaction**:
    - Sends notification events to the **Notification Microservice** for email delivery.
    - Consumes events published by **API Letters** and **API Package** microservices.

### **Features**

1. **Event-Driven Processing**:
    - Handles **`LetterEvent`** and **`PackageEvent`** through Kafka topics, ensuring asynchronous and scalable communication.
    - Publishes **`NotificationEvent`** for downstream services, decoupling responsibilities.
2. **Reactive Programming**:
    - Utilizes Project Reactor for non-blocking, asynchronous stream processing, enhancing performance and resource efficiency.
3. **Eligibility Validation**:
    - Implements dedicated services for validating letters and packages (**`LetterEligibilityService`** and **`PackageEligibilityService`**).
    - Provides clean, extendable logic for eligibility checks.
4. **Kafka Integration**:
    - Processes events from input Kafka topics and routes them to corresponding services or processors.
    - Publishes processed results to output topics for consumption by other microservices.

---

### **Architecture**

1. **Clean Code and Pragmatic Design**:
    - **Separation of Concerns (SoC)**:
        - Dedicated layers for processing (**`ProcessorSpec`**), business logic (services), and event definitions.
    - **Domain-Driven Design (DDD)**:
        - Uses clearly defined entities (**`LetterEvent`**, **`PackageEvent`**, **`NotificationEvent`**) to represent the core business domain.
2. **DRY (Don't Repeat Yourself)**:
    - Abstracts common processing logic into reusable interfaces like **`ProcessorSpec`**.
    - Shared event models (**`common.event`**) reduce redundancy across components.
3. **SOLID Principles**:
    - **Single Responsibility Principle (SRP)**:
        - Each service (e.g., **`LetterEligibilityService`**, **`PackageEligibilityService`**) handles a single responsibility.
    - **Open/Closed Principle (OCP)**:
        - Processors (**`LetterEligibleProcessor`**, **`PackageEligibleProcessor`**) are open for extension through **`ProcessorSpec`** but closed for modification.
    - **Liskov Substitution Principle (LSP)**:
        - Service implementations (**`LetterEligibilityServiceImpl`**, **`PackageEligibilityServiceImpl`**) can be substituted for their interfaces without breaking functionality.
    - **Interface Segregation Principle (ISP)**:
        - Interfaces like **`ProcessorSpec`** define specific behaviors, avoiding overloading of responsibilities.
    - **Dependency Inversion Principle (DIP)**:
        - High-level components depend on abstractions (**`ProcessorSpec`**, **`LetterEligibilityService`**), not concrete implementations.
4. **Scalability and Maintainability**:
    - Modular design allows independent updates to processors and services.
    - Kafka integration ensures the system scales effortlessly with increasing event volumes.

---

## **Project Structure**

The project follows a modular structure to separate concerns effectively.

```rust
src/main/java/com/emailkafkamicroservice
├── common
│   └── event
│       ├── LetterEvent.java          // Represents letter eligibility event
│       ├── NotificationEvent.java    // Represents a notification event
│       └── PackageEvent.java         // Represents package eligibility event
├── config
│   └── EventHandler.java             // Configures Kafka stream bindings
├── processor
│   ├── ProcessorSpec.java            // Interface for stream processing logic
│   └── impl
│       ├── LetterEligibleProcessor.java // Handles letter eligibility logic
│       └── PackageEligibleProcessor.java // Handles package eligibility logic
├── service
│   ├── LetterEligibilityService.java    // Interface for letter eligibility logic
│   ├── PackageEligibilityService.java   // Interface for package eligibility logic
│   └── impl
│       ├── LetterEligibilityServiceImpl.java // Implementation of letter logic
│       └── PackageEligibilityServiceImpl.java // Implementation of package logic
└── Application.java                  // Entry point for the microservice

```

---

## **Interaction with Other Microservices**

### **Inputs:**

- **API Letters** publishes **`LetterEvent`** to the Kafka topic **`letter_created`**.
- **API Package** publishes **`PackageEvent`** to the Kafka topic **`package_created`**.

### **Outputs:**

- Publishes **`NotificationEvent`** to the Kafka topic **`new-notification-event`**:
    - Consumed by **Notification Microservice** to send email notifications.

---

## **Configuration**

The service is configured in **`application.yaml`**:

### **Kafka Configuration**

```yaml
spring:
  cloud:
    stream:
      kafka:
        binder:
          brokers: localhost:9092
          configuration:
            replication.factor: 3
            auto.create.topics.enable: true
      bindings:
        letterBinding-in-0:
          destination: letter_created
        letterBinding-out-0:
          destination: new-notification-event
        packageBinding-in-0:
          destination: package_created
```

### **Database Configuration**

The service uses PostgreSQL with R2DBC:

```yaml
spring:
  r2dbc:
    url: r2dbc:postgresql://localhost:5432/email-db
    username: postgres
    password: 1234
```

> [!NOTE]
> Make sure to replace the database URL, username, and password with your actual configuration.

---

## **Testing the Service**

### **Unit Testing**

Unit tests ensure business logic correctness. Run tests with:

```bash
mvn test
```

### **Integration Testing**

- Use a local Kafka setup with Docker or Kafka TestContainers.
- Example Docker setup:

```bash
docker-compose up -d
```

- Simulate event publication using tools like **`kafka-console-producer`**.

### **Manual Testing**

1. **Send a Kafka Event**:
    - Use **`kafka-console-producer`** to publish to **`letter_created`** or **`package_created`** topics.

    ```bash
    echo '{"trackingNumber": 123, "isEligible": true, "receiverEmail": "test@example.com"}' | kafka-console-producer --broker-list localhost:9092 --topic letter_created
    ```

2. **Consume Notification Events**:
    - Check the **`new-notification-event`** topic using **`kafka-console-consumer`**.

    ```bash
    kafka-console-consumer --bootstrap-server localhost:9092 --topic new-notification-event --from-beginning
    ```


---

## **How to Run the Service**

### **Prerequisites**

- **Kafka**: Ensure a running Kafka broker (e.g., via Docker).
- **PostgreSQL**: Set up the database as per the configuration.

### **Steps to Run**

1. Build the service:

    ```bash
    mvn clean install
    ```

2. Start the application:

    ```bash
    java -jar target/email-kafka-microservice-0.0.1-SNAPSHOT.jar
    ```


---

## **Example Scenarios**

1. **Letter Eligibility Validation**:
    - A letter is created in the **API Letters** service.
    - A **`LetterEvent`** is published to Kafka.
    - This microservice validates the letter's eligibility and publishes a **`NotificationEvent`** if eligible.
2. **Package Eligibility Validation**:
    - A package is created in the **API Package** service.
    - A **`PackageEvent`** is published to Kafka.
    - This microservice validates the package's eligibility and publishes a processed event.
3. **Notification Delivery**:
    - The **Notification Microservice** consumes **`NotificationEvent`** and sends an email to the user.

---

## Related Microservices

The system consists of multiple microservices that work together to provide comprehensive functionality. Below is a list of all the microservices in the system, with links to their respective repositories:

- [**users-service-api**](https://github.com/juansebstt/users-service-api): Handles user management, including registration, profile updates, and account data.
- [**email-kafka-microservice**](https://github.com/juansebstt/email-kafka-microservice): Manages asynchronous email event processing using Kafka for reliable messaging.
- [**notifications-microservice-api**](https://github.com/juansebstt/notifications-microservice-api): Sends notifications based on triggered events from other services.
- [**email-authentication-service-api**](https://github.com/juansebstt/email-authentication-service-api): Manages email-based authentication and verification processes.
- [**email-api-gateway**](https://github.com/juansebstt/email-api-gateway): Serves as the entry point for routing requests to various microservices.
- [**letter-service-api**](https://github.com/juansebstt/letter-service-api): Manages letters, including creation, storage, and retrieval.
- [**packages-service-api**](https://github.com/juansebstt/packages-service-api): Manages package-related operations, including tracking and status updates.

## **Key Notes**

- **Scalability**:
    - The reactive nature of this service allows it to handle large volumes of events efficiently.
    - Kafka ensures distributed and fault-tolerant event handling.
- **Extensibility**:
    - Additional event types or processing logic can be integrated with minimal changes.

This microservice serves as the central point for handling eligibility validation and notification logic in the system, integrating seamlessly with other microservices.