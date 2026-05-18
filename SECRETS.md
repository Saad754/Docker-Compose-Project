# Secrets and Environment Variables

This project requires environment files that are not committed to the repo for security reasons.
Copy the variables from `env.example` and fill in the values.

---

## Environment Files

### mysql.env (MySQL)
| Variable | Description |
|---|---|
| MYSQL_ROOT_PASSWORD |Password of the mysql client|
| MYSQL_DATABASE |The database created at startup|
| MYSQL_USER |The mysql user created at startup|
| MYSQL_PASSWORD |Password of the user created|

### keycloak.env (Keycloak)
| Variable | Description |
|---|---|
| KEYCLOAK_ADMIN |Username of user with admin permission in KeyCloak|
| KEYCLOAK_ADMIN_PASSWORD |Password of admin user|
| KC_DB |The database where KeyCloak will store data|
| KC_DB_URL |URL of database where keycloak will store the data|
| KC_DB_USERNAME |Database username that keycloak will use to access the database|
| KC_DB_PASSWORD |Password of user used to access KeyCloak's database|

### springboot.env (Spring Boot)
| Variable | Description |
|---|---|
| SPRING_DATASOURCE_URL |Application database URL|
| SPRING_DATASOURCE_USERNAME |Username of the user used to access the database|
| SPRING_DATASOURCE_PASSWORD |Password of user|
| SPRING_REDIS_HOST |Name of the redis service|
| SPRING_REDIS_PORT |Redis port used|
| SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI |URL of the issuer (KeyCloak)|

---

## Important Dependencies

List any variables that must match each other here.
For example:
 - The KC_DB_USERNAME, KC_DB ,and KC_DB_PASSWORD must be set the same as the ones created in the mysql database

---

## Setup Steps for New Developers
 - Clone the Repo
 - Put the Keycloak env variables in a file named keycloak.env
 - Put the SpringBoot env variables in a file named springboot.env
 - Put the MySql env variables in a file named mysql.env
 - Change into the project directory and run: docker compose up -d 

### Notes
 - init.sql script is run when the sql database is deployed for the first time and it creates the Keycloak database and user which allows KeyCloak to connect to database during first run. If you want to change the username and password make sure that the KeyCloak enviroment variables and the init.sql script are changed accordingly.
 - The realm used in KeyCloak is auto imported from the realm.json file also at startup
---

## Production Note

Note that the enviromental variables arent stored securely (plain text) . In a production enviorment they should be stored using a tool like HashiCorp Vault

## Production Keycloak Notes

- Replace `start-dev` with `start` in docker-compose.yml
- Set up a reverse proxy (Nginx) in front of Keycloak to handle HTTPS
- Never expose Keycloak port 8080 publicly
- Disable admin console access from public internet
- Use strong passwords for all credentials
- Store all secrets in HashiCorp Vault instead of env files