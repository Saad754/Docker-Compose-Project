# Multi-Container Docker Compose Stack

A training project to learn Docker Compose by deploying 
a multi-container application with MySQL, Redis, React, SpringBoot and Keycloak.

## Services
There are 5 containers : 
 - MySql Database container for app database and KeyCloak data
 - Redis for cache of app
 - KeyCloak for authentication and authorization
 - Frontend (currently placeholder using nginx image)
 - Backend (A placeholder Springboot image)

## Prerequisites
 - Docker and Docker Compose
 - Git
## How to Run
 - Clone the Repo
 - Put the env variable in env.example in their respective files. Look at SECRETS.md for refrence
 - Change into the project directory and run: docker compose up -d 

## How to Stop
 - Run docker compose down (Keeps data and volumes)
 - Run docker compose down -v (Deletes volumes and Data)

## Project Structure

#### docker-compose.yml
Contains all the services and their configurations

#### env.example
Contains all the env variables needed for project

#### init.sql
Contains the startup script of mysql which makes sure that Keycloak can connect to the database

#### README.md
Readme file

#### realm.json
Contains the exported configurations of the realm used in KeyCloak 

#### SECRETS.md
Contains detailed information of all env vars and their uses
