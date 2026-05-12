# Docker Compose Learning Project

A training project to learn Docker Compose by deploying 
a multi-container application with MySQL, Redis, and Keycloak.

## Services
- MySQL 8.0
- Redis 7.2
- Keycloak 23.0 (backed by MySQL)

## How to Run
1. Copy env.example to Conf.env and keycloak.env and fill in values
2. Create Keycloak database in MySQL (see setup guide below)
3. Run: docker compose up -d

## Stack
- Week 1: MySQL + Redis
- Week 2: Keycloak with MySQL backend and realm auto-import
- Week 3: Spring Boot + React (coming soon)