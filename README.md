# Askend test asignment - Filters App

A full-stack application for creating and managing filters with dynamic criteria. Built with Java 25, Spring Boot 4, Angular, and PostgreSQL.

---

## Running the App

### 1. Set up environment variables

Copy the example env file and fill in your database credentials:

```bash
cp .env.example .env
```

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

### 4. Start the frontend

```bash
cd frontend
npm install
ng serve
```

Open **http://localhost:4200** in your browser.

---

## Prerequisites

You need Java 25+, Maven 3.9+, Node.js 20+, Angular CLI (`npm install -g @angular/cli`), and Docker installed before running the steps above.
