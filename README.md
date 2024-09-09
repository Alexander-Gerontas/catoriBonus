# CatoriTech Assessment

## Bonus Microservice with Kafka

This project implements a Bonus Microservice that listens for player login events via Kafka, calculates a bonus for the player, and updates the player's bonus in the database. The project is built using Spring Boot 3.2.0 and Java 17, with Kafka being used for communication. The bonus and login services can be split into seperate projects.

## Table of Contents
- [Features](#features)
- [Technologies](#technologies)
- [Setup and Running the Application](#setup-and-running-the-application)
   - [Prerequisites](#prerequisites)
   - [Running Kafka, Zookeeper, and PostgreSQL using Docker](#running-kafka-zookeeper-and-postgresql-using-docker)
   - [Running the Spring Boot Application](#running-the-spring-boot-application)
- [API Endpoints](#api-endpoints)
- [Kafka Topics](#kafka-topics)
- [License](#license)

## Features
- Bonus service listens for player login events from the Kafka topic `player-login-events`.
- Calculates and updates player bonuses.
- Publishes bonus updates to the Kafka topic `player-bonus-updates`.
- Uses PostgreSQL for storing player bonus data.

## Technologies
- Java 17
- Spring Boot 3.2.0
- Spring Kafka
- Spring Data JPA
- PostgreSQL
- Docker for running Kafka and Zookeeper

## Setup and Running the Application

### Prerequisites
- Docker should be installed on your system.
- Java 17 should be installed.

### Running Kafka, Zookeeper, and PostgreSQL using Docker
The application uses Kafka and PostgreSQL, which you can run using the provided Docker script. Follow these steps:

1. Clone the repository and navigate to the project directory.
2. Run the Docker compose script.
   The script will spin up Kafka, Zookeeper, and PostgreSQL using Docker.

### Running the Spring Boot Application

Once the Docker services are running, you can start the Spring Boot application using Maven. This will start the Bonus Microservice on port 8080.

### API Endpoints

1. **Login Event (Simulates player login)**
    - **URL:** `/api/v1/login`
    - **Method:** POST
    - **Request Body:**
      ```json
      {
        "playerId": 1
      }
      ```
    - **Description:** Sends a login event for the player with the given `playerId`. The service will process the event, calculate a bonus, and store the updated bonus in the database.

### Kafka Topics

- **player-login-events:** This topic receives events when a player logs in. The Bonus Microservice listens to this topic to process the login event and calculate the bonus.
- **player-bonus-updates:** After processing a login event and updating the player's bonus, the service publishes an event to this topic to notify other services of the updated bonus.
