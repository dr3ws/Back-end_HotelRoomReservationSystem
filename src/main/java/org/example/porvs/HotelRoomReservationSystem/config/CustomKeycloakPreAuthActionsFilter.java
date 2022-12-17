package org.example.porvs.HotelRoomReservationSystem.config;

import org.keycloak.adapters.spi.HttpFacade;
import org.keycloak.adapters.spi.UserSessionManagement;
import org.keycloak.adapters.springsecurity.facade.SimpleHttpFacade;
import org.keycloak.adapters.springsecurity.filter.KeycloakPreAuthActionsFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomKeycloakPreAuthActionsFilter extends KeycloakPreAuthActionsFilter {
    public CustomKeycloakPreAuthActionsFilter(UserSessionManagement userSessionManagement) {
        super(userSessionManagement);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpFacade facade = new SimpleHttpFacade((HttpServletRequest) request, (HttpServletResponse) response);

        if (facade.getRequest().getRelativePath().equals("/error")) {
            return;
        }

        super.doFilter(request, response, chain);
    }
}
