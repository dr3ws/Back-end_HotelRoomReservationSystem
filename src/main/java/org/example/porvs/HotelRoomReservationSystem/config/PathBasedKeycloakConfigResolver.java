package org.example.porvs.HotelRoomReservationSystem.config;

import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.spi.HttpFacade;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.springframework.http.server.PathContainer;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class PathBasedKeycloakConfigResolver implements KeycloakConfigResolver {
    private static final String FRONT_API = "front";
    private static final PathPattern FRONT_API_PATTERN = new PathPatternParser().parse("/api/v?/front/**");

    private final MultitenantKeycloakProperties multitenantKeycloakProperties;
    private final KeycloakSpringBootProperties keycloakSpringBootProperties;

    private KeycloakDeployment defaultKeycloakDeployment;

    private final Map<String, KeycloakDeployment> deploymentCache = new ConcurrentHashMap<>();

    @PostConstruct
    private void init() {
        defaultKeycloakDeployment = KeycloakDeploymentBuilder.build(keycloakSpringBootProperties);
    }

    @Override
    public KeycloakDeployment resolve(HttpFacade.Request request) {
        if (FRONT_API_PATTERN.matches(PathContainer.parsePath(request.getRelativePath()))) {
            if (!multitenantKeycloakProperties.getApis().containsKey(FRONT_API)) {;
//                throw new Exception("There are no keycloak properties for front API");
            }
            return deploymentCache.computeIfAbsent(FRONT_API,
                    deployment -> KeycloakDeploymentBuilder.build(multitenantKeycloakProperties.getApis().get(FRONT_API)));
        }
        return defaultKeycloakDeployment;
    }
}