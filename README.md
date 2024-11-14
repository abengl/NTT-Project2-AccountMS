# Banking System-Account Microservice

This Account microservice is part of a banking system designed to handle CRUD operations for banking accounts and transactions. It provides endpoints for creating, retrieving, updating, and deleting accounts, as well as managing transactions associated with each account. This microservice is built using Spring Boot, integrates with MySQL, and follows principles of both Object-Oriented Programming (OOP) and functional programming. Documentation is available via Swagger/OpenAPI, and a Postman collection is provided for testing.

## Table of Contents
- [Project Overview](#project-overview)
- [Technologies and Approaches](#technologies-and-approaches)
- [UML Diagrams](#uml-diagrams)
- [Running the Postman Collection](#running-the-postman-collection)
- [Accessing Swagger/OpenAPI Documentation](#accessing-swaggeropenapi-documentation)

## Project Overview
The Account microservice is a standalone module within a broader banking system. It manages:
- Bank accounts (creation, deletion, retrieval)
- Transactions (deposits, withdrawals)

## Technologies and Approaches
- **Spring Boot**: Provides the foundation for creating RESTful APIs and microservices with an embedded server.
- **OpenAPI/Swagger**: Used for API documentation and testing.
- **Functional Programming and OOP**: Functional programming techniques are applied for data validation, while OOP is used to structure the main services and domain models.
- **MySQL Database**: Stores account and transaction information.
- **Postman**: Used for API testing with a pre-configured collection for CRUD operations.

## UML Diagrams
The following UML diagrams illustrate the architecture and data flow of the Customer Microservice:

- **Sequence Diagram**: Details the typical flow of operations between the Customer and [Account microservices](https://github.com/abengl/NTT-Project2-AccountMS).
  <img alt="UML sequence diagram" src="https://github.com/abengl/NTT-Project2-AccountMS/blob/a3ccfe7ae3fad6d2d667ecc89dded8d9d2906986/src/main/resources/static/UML_Sequence_Diagram2_Microservices.png" width="500" height="500">
- **Component Diagram**: Show the overall architecture of the microservices.
    <img alt="UML sequence diagram" src="https://github.com/abengl/NTT-Project2-AccountMS/blob/a3ccfe7ae3fad6d2d667ecc89dded8d9d2906986/src/main/resources/static/UML_Component_Diagram_Microservices.png" width="800" height="500">

## Running the Postman Collection
A Postman collection is provided to test the Account microservice’s endpoints. Follow these steps:

1. Import the Collection: Download or clone the repository, then import the Postman collection file located in the /postman directory.
2. Configure Environment Variables: Ensure that the Postman environment variables for your MySQL database connection (if applicable) and service URL are set correctly.
3. Run Tests: Once configured, you can execute requests to test each endpoint. The collection provides requests for creating, retrieving, updating, and deleting customer records.

## Accessing Swagger/OpenAPI Documentation
The Swagger/OpenAPI documentation provides a detailed description of each endpoint, including parameters, request bodies, and response formats.

1. Ensure the service is running locally on `http://localhost:8086`.
2. Open a browser and go to: `http://localhost:8086/swagger-ui.html`
   

