# Fishing App Server

Server source code for the Fishing App.

## Stack
* Java SE 21.0.4
* Spring Boot v3.1.5
* Apache Maven v3.9.1
* Docker Desktop v4.24.2
* PostgreSQL v16.0

## Server Architecture Overview

- Layered architecture
    - Presentation layer
    - Service layer
    - Data Access layer
        - Domain Model
        - Repository

General architecture of interfaces and classes using Spring Boot:

- Controllers
- Services
- Mappers
- DTOs
- Entities
- Repositories

Broad example of Trip architecture:

A client POST request arrives at the TripController. The JSON data is validated & sanitized, then processed into a TripDto. The TripDto is sent to the TripService where business logic is applied. The TripService uses the TripMapper to convert the TripDto to a TripEntity & sends the TripEntity to the TripRepository. The TripRepository interacts with the database to create the TripEntity in the database.

Security implementation includes Spring Security with JSON Web Tokens, & all passwords are salted & hashed in the database.