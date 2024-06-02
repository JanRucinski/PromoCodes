# PromoCodes

PromoCodes is a Java Spring application designed to manage discount codes for sales or promotions (also known as promo codes). This application provides REST endpoints required for all neccesary operations listed in TASK.md.

## Features

- **In Memory Database**
- **Product Endpoints**
- **Promo Code Endpoints**
- **Price Calculator Endpoints**
- **Purchases Endpoints**
- **Unit and Integration Testing**: Written in Groovy with Spock Framework
- **Swagger API Documentation**: Interactive API documentation for easy integration and testing.

## Prerequisites

- Java Development Kit (JDK) 17 or later
- Maven (for building the project)
- An IDE (e.g., IntelliJ IDEA, Eclipse) is recommended for running and debugging

## Installation

1. **Clone the repository:**

   ```
   git clone https://github.com/JanRucinski/PromoCodes.git
   cd PromoCodes
    ```

2. **Build the project using Maven:**
    ```
    mvn clean install
    ```

2. **Run the application:**

    You can run the application from your IDE or use the following command:

    ```
    java -jar target/PromoCodes-1.0-SNAPSHOT.jar
    ```

## API Documentation

The application includes Swagger API documentation for interacting with its backend services. To access the Swagger UI:

1. Start the PromoCodes application.
2. Open a web browser and navigate to http://localhost:8080/swagger-ui.html.


## Postman collection

The project includes a Postman collection for easier API testing
