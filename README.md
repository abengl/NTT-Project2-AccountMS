# Banking System-Account Microservice

This Account microservice is part of a banking system designed to handle CRUD operations for banking accounts. It provides endpoints for creating, retrieving, updating, and deleting accounts, as well as managing transactions associated with each account. This microservice is built using Spring Boot, integrates with MySQL, and follows principles of both Object-Oriented Programming (OOP) and functional programming. Documentation is available via Swagger/OpenAPI, and a Postman collection is provided for testing, as well as, unit tests with JUnit, Mockito. Code coverage is tracked with JaCoCo and code validation with Checkstyle.

## Table of Contents
- [Technologies and Approaches](#technologies-and-approaches)
- [UML Diagrams](#uml-diagrams)
- [Postman](#postman)
- [Swagger/OpenAPI Documentation](#swaggeropenapi-documentation)
- [Code Quality and Coverage](#code-quality-and-coverage)

## Technologies and Approaches
- **Spring Boot**: Provides the foundation for creating RESTful APIs and microservices with an embedded server.
- **OpenAPI/Swagger**: Used for API documentation and testing.
- **Functional Programming and OOP**: Functional programming techniques are applied for data validation, while OOP is used to structure the main services and domain models.
- **MySQL Database**: Stores account and transaction information.
- **Postman**: Used for API testing with a pre-configured collection for CRUD operations.
- **JUnit-Mockito**: For unit testing the main classes of the service.
- **JaCoCo-Checkstyle**: To verify code covergae and best practices in code style.

## UML Diagrams
The following UML diagrams illustrate the architecture and data flow of the Account Microservice:

- **Sequence Diagram**: Details the typical flow of operations between the Account and [Customer microservices](https://github.com/abengl/NTT-Project2-CustomerMS).
  <img alt="UML sequence diagram" src="https://github.com/abengl/NTT-Project2-AccountMS/blob/a3ccfe7ae3fad6d2d667ecc89dded8d9d2906986/src/main/resources/static/UML_Sequence_Diagram2_Microservices.png" width="500" height="500">
- **Component Diagram**: Show the overall architecture of the microservices.
    <img alt="UML sequence diagram" src="https://github.com/abengl/NTT-Project2-AccountMS/blob/a3ccfe7ae3fad6d2d667ecc89dded8d9d2906986/src/main/resources/static/UML_Component_Diagram_Microservices.png" width="800" height="400">

## Postman
A Postman collection is provided to test the Account microservice’s endpoints. Follow these steps:
1. **Import the Collection**: Download or clone the repository, then import the Postman collection file located in the `/postman` directory.
2. **Import Environment Variables**: import the environment variables into Postman and set them to run with the test collection.
3. **Run Tests**: Once configured, you can execute requests to test each endpoint. The collection provides requests for creating, retrieving, updating, and deleting customer records.

## Swagger/OpenAPI Documentation
The Swagger/OpenAPI documentation provides a detailed description of each endpoint, including parameters, request bodies, and response formats.
1. Ensure the service is running locally on `http://localhost:8086`.
2. Open a browser and go to: [http://localhost:8086/v1/swagger-ui.html](http://localhost:8086/v1/swagger-ui.html)
   
## Code Quality and Coverage

To maintain code quality and ensure adequate test coverage, the project uses **Checkstyle** for code analysis and **JaCoCo** for test coverage reports. Follow the steps below to run these tools:

### Run Checkstyle
1. Open a terminal and navigate to the project directory.
2. Run the following command to perform a Checkstyle analysis:
   ```bash
   mvn checkstyle:check
   ```
3. Review the output in the terminal for any code style violations. The results will also be saved in the target/reports/checkstyle.html file.
   
### Run JaCoCo for Test Coverage
1. In the terminal, run the tests with coverage analysis:
   ```bash
   mvn clean test
   ```
2. Open the generated report located at `target/site/jacoco/index.html` in your browser to review coverage details.

