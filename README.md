
Hotel Booking REST API

#Description:-
This project is a REST API for managing hotel bookings. It provides endpoints for creating, updating, and deleting users, hotels, rooms, bookings, booking details, and payments.

#Documentation
The API endpoints are documented using Swagger, which provides a user-friendly interface for exploring and testing the endpoints.
Access the Swagger documentation by navigating to /swagger-ui.html i.e http://localhost:8083/hotelbooking/swagger-ui/index.html after running the application.
Explore the available endpoints, view request and response schemas, and test the API directly from the Swagger UI.

Database Configuration
- The application is connected to a MYSQL database to store and retrieve data.
- The database URL is `jdbc:mysql://localhost:3306/hotel_booking`, where:
  - `jdbc:mysql://` indicates the JDBC connection protocol for MySQL.
  - `localhost:3306` represents the host and port where MySQL is running. Update this if your database is hosted elsewhere or uses a different port.
  - `hotel_booking` is the name of the database schema used by the application.


In application.properties you need to change 

spring.datasource.url='Your Database url'

spring.datasource.username='Your Username'

spring.datasource.password='Your Password'

Technologies Used:-
Spring Boot
Spring Data JPA
Spring Web MVC
Hibernate
MySQL 



#Port Number - 8083

#Contextpath - /hotelbooking
