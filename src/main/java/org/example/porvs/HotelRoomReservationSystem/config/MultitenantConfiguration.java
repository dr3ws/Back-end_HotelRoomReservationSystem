package org.example.porvs.HotelRoomReservationSystem.config;

import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(KeycloakSpringBootProperties.class)
public class MultitenantConfiguration {
    private final MultitenantKeycloakProperties multitenantKeycloakProperties;
    private final KeycloakSpringBootProperties keycloakSpringBootProperties;

    @Bean
    public KeycloakConfigResolver keycloakConfigResolver() {
        return new PathBasedKeycloakConfigResolver(multitenantKeycloakProperties, keycloakSpringBootProperties);
    }
}