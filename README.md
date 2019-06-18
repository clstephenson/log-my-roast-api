# Summary
This application is an application I'm building to learn more about RESTful APIs and Spring Boot, and is still a work
in progress. It's purpose is to provide a log for home coffee roasters. This will be the API that which connects to a 
database and a front-end will be created as a separate project.

# Purpose
- Learn Spring Boot
- Learn RESTful APIs
- Learn and practice writing tests
- Create application to log and track home coffee roasts

# API Documentation (in work)

## Sources
#### Source JSON Structure

```json
[
  {
    "id": "integer value",
    "name": "string value"
  }
]
```
required fields: name

#### API Endpoints

| Purpose       | Method | Endpoint          | Status Codes | Request Body                | Response Body     |
| ------------- | ------ | ----------------- | ------------ | --------------------------- | ----------------- |
| Get All       | GET    | /api/sources      | 200          | n/a                         | JSON (all fields) |
| Get by ID     | GET    | /api/sources/{id} | 200, 404     | n/a                         | JSON (all fields) |
| Create Source | POST   | /api/sources      | 201          | JSON                        | JSON (all fields) |
| Update Source | PUT    | /api/sources/{id} | 200, 404     | JSON (all fields with data) | JSON (all fields) |
| Delete Source | DELETE | /api/sources/{id} | 204, 404     | n/a                         | n/a               |

## Coffee Beans
#### Source JSON Structure

```json
[
  {
    "id": "integer value",
    "name": "string value"
  }
]
```
required fields: name

#### API Endpoints

| Purpose       | Method | Endpoint          | Status Codes | Request Body                | Response Body     |
| ------------- | ------ | ----------------- | ------------ | --------------------------- | ----------------- |
| Get All       | GET    | /api/sources      | 200          | n/a                         | JSON (all fields) |
| Get by ID     | GET    | /api/sources/{id} | 200, 404     | n/a                         | JSON (all fields) |
| Create Source | POST   | /api/sources      | 201          | JSON                        | JSON (all fields) |
| Update Source | PUT    | /api/sources/{id} | 200, 404     | JSON (all fields with data) | JSON (all fields) |
