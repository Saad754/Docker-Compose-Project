package com.example.itemsapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configures the application as a stateless OAuth2 Resource Server.
     * Every request to /api/** must carry a valid JWT Bearer token.
     * The token is validated against the Keycloak issuer URI defined in application.properties
     * (overridable via the SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI env variable).
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF — not needed for stateless REST APIs
            .csrf(csrf -> csrf.disable())

            // Stateless session — no HttpSession will be created or used
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Protect all /api/** endpoints; everything else is also authenticated by default
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/**").authenticated()
                .anyRequest().authenticated()
            )

            // Configure as JWT-based OAuth2 Resource Server
            // Spring reads spring.security.oauth2.resourceserver.jwt.issuer-uri
            // and fetches the JWKS endpoint automatically from Keycloak's discovery document
            .oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwt -> jwt
                .jwkSetUri(System.getenv().getOrDefault(
                    "SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_JWK_SET_URI",
                    "http://keycloak:8080/realms/myrealm/protocol/openid-connect/certs"
                ))
            )
        );
                        

        return http.build();
    }
}
