# items-api

A minimal Spring Boot 3 REST API that exposes a JWT-protected endpoint for listing items, backed by MySQL and secured via a Keycloak-issued OAuth2 token.

---

## Tech stack

| Layer | Technology |
|---|---|
| Runtime | Java 17 |
| Framework | Spring Boot 3.2 |
| Persistence | Spring Data JPA + MySQL |
| Security | Spring Security + OAuth2 Resource Server (JWT) |
| Identity provider | Keycloak |

---

## Project structure

```
spring-items-api/
├── pom.xml
└── src/
    └── main/
        ├── java/com/example/itemsapi/
        │   ├── ItemsApiApplication.java       # Entry point
        │   ├── config/
        │   │   └── SecurityConfig.java        # JWT / stateless security config
        │   ├── controller/
        │   │   └── ItemController.java        # GET /api/items
        │   ├── entity/
        │   │   └── Item.java                  # JPA entity
        │   └── repository/
        │       └── ItemRepository.java        # Spring Data repository
        └── resources/
            ├── application.properties         # Configuration (env-var backed)
            └── data.sql                       # Seed data (3 sample items)
```

---

## Configuration

All sensitive or environment-specific values are driven by environment variables.  
Defaults are provided for local development.

| Environment Variable | Default | Description |
|---|---|---|
| `SPRING_DATASOURCE_URL` | `jdbc:mysql://localhost:3306/itemsdb` | JDBC connection URL |
| `SPRING_DATASOURCE_USERNAME` | `root` | DB username |
| `SPRING_DATASOURCE_PASSWORD` | `secret` | DB password |
| `SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI` | `http://localhost:8080/realms/myrealm` | Keycloak realm issuer URI |

---

## Running locally

### 1. Start MySQL

```bash
docker run -d \
  --name mysql \
  -e MYSQL_ROOT_PASSWORD=secret \
  -e MYSQL_DATABASE=itemsdb \
  -p 3306:3306 \
  mysql:8
```

### 2. Start Keycloak

```bash
docker run -d \
  --name keycloak \
  -e KEYCLOAK_ADMIN=admin \
  -e KEYCLOAK_ADMIN_PASSWORD=admin \
  -p 8080:8080 \
  quay.io/keycloak/keycloak:24.0 start-dev
```

Create a realm (e.g. `myrealm`) and a client, then note the issuer URI:
`http://localhost:8080/realms/myrealm`

### 3. Build and run

```bash
export SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/itemsdb
export SPRING_DATASOURCE_USERNAME=root
export SPRING_DATASOURCE_PASSWORD=secret
export SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI=http://localhost:8080/realms/myrealm

./mvnw spring-boot:run
```

The API will be available at `http://localhost:8081`.

---

## API usage

### Get all items (authenticated)

```bash
# 1. Obtain a token from Keycloak
TOKEN=$(curl -s -X POST \
  "http://localhost:8080/realms/myrealm/protocol/openid-connect/token" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "client_id=<your-client-id>" \
  -d "client_secret=<your-client-secret>" \
  -d "grant_type=client_credentials" \
  | jq -r '.access_token')

# 2. Call the endpoint
curl -H "Authorization: Bearer $TOKEN" http://localhost:8081/api/items
```

### Expected response

```json
[
  { "id": 1, "name": "Laptop" },
  { "id": 2, "name": "Wireless Mouse" },
  { "id": 3, "name": "Mechanical Keyboard" }
]
```

### Unauthenticated request

```bash
curl -i http://localhost:8081/api/items
# HTTP/1.1 401 Unauthorized
```

---

## Running tests

```bash
./mvnw test
```

Tests use `@WithMockUser` to bypass real JWT validation, so no running Keycloak instance is needed.
