# Askend test asignment - Filters App

A full-stack application for creating and managing filters with dynamic criteria. Built with Java 25, Spring Boot 4, Angular, and PostgreSQL.

---

## Prerequisites

You need Java 25+, Maven 3.9+, Node.js 20+, Angular CLI, and Docker installed before running the steps below.


## Running the App

### 1. Set up environment variables

Copy .env.example to .env and fill in your PostgreSQL credentials."

### 2. Start the database

```bash
docker compose up -d
```

### 3. Start the backend

```bash
cd backend
mvn spring-boot:run
```

Runs on **http://localhost:8080**. Tables and sample data are created automatically on first startup.

Swagger UI is available at: http://localhost:8080/swagger-ui/index.html

### 4. Start the frontend

```bash
cd frontend
npm install
ng serve
```

Open **http://localhost:4200** in your browser.
