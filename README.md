# Bouldering Tracker – Full-Stack Application

## Overview
A multi-user web application for bouldering enthusiasts to log, track, and share climbs. It includes:

Frontend: A responsive React interface for managing climbs and interacting with other users.
Backend: A Spring Boot API for authentication, climb management, and location-based filtering.


## Tech Stack
### Frontend

- React 18 (Hooks, Context API)
- React Router
- TailwindCSS
- Axios
- Heroicons

### Backend

- Spring Boot 3+
- JPA/Hibernate
- REST API with Swagger/OpenAPI
- MySQL/PostgreSQL (configurable)

## Features

- Authentication: Secure login & registration.
- Climb Logging:

  - Grade (V or Font scale)
  - Features (autocomplete)
  - Location (with GPS option)
  - Photo upload
  - Rating & attempts
  - Public/private sharing


## Frontend Extras:

- Dark mode
- Mobile responsiveness
- Location-based filtering


## Backend Extras:

- DTO layers for clean API
- ERD diagram for database design


## Future Enhancements:

- Analytics dashboard
- Offline support
- Social features (likes, comments)

## Setup Instructions
### Backend
```
# Clone and run backend
cd backend
./mvnw spring-boot:run
```
Configure application.properties for database

###Frontend
```
Shell# Clone and run frontend
cd frontend
npm install
npm run dev
```

## API Documentation
- Swagger UI:
http://localhost:9001/swagger-ui.html

- Postman Collection Runner:
https://kushal-zuk-9525202.postman.co/workspace/My-Workspace~e3933dfd-ea62-4e38-9df5-57cd0ffaa852/collection/49807865-9e0ffec3-c703-4b9f-b07d-03d325cfaae6?action=share&creator=49807865

## Media
- Wireframe: frontend/media/wireframe.pdf
- ERD Diagram: backend/media/Bouldering_App_ERD.png
