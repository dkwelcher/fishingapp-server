# Fishing App Server

Server code for Fishing App project for CSCI 592

## How to Run (02/12/2024)

1. Ensure you have the following installed:
    - Docker Desktop v4.24.2, [Download from Official Release Notes](https://docs.docker.com/desktop/release-notes/)
      - Most recent version is incompatible with Spring Boot v3.1.5. Use the above version.
    - PostgreSQL 16.0, [Download from Official Site](https://www.postgresql.org/download/)
2. Use command: `docker-compose up` to containerize server code.
3. Run the application from: src/main/java/com/fishinglog/fishingapp/**FishingAppApplication.java**

At this point, because user id & username are hardcoded in the client (src\views\ManageTrips.jsx), you will need to manually insert a User into the database.

Use the Postman API ([https://www.postman.com/](https://www.postman.com/)) to send a POST query to the server.

POST http://localhost:8080/users

Body (raw JSON):
`{
"username": "someUser",
"password": "someUserPassword@1",
"email": "someUser@email.com"
}`

Remember your username.

Next, download & install DBeaver Universal Database Tool (https://dbeaver.io/). Use this tool to look inside the database for your User id.

Go to src\views\ManageTrips.jsx in the client code and replace the hardcoded user useState.