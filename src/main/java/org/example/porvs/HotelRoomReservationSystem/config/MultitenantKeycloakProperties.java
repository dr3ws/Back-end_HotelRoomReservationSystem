package org.example.porvs.HotelRoomReservationSystem.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Setter
@ToString
@Component
@ConfigurationProperties(prefix = "multitenant-keycloak", ignoreUnknownFields = false)
public class MultitenantKeycloakProperties {
    private Map<String, KeycloakSpringBootProperties> apis;
}
