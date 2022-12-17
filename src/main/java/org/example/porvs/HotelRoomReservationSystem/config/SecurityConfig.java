package org.example.porvs.HotelRoomReservationSystem.config;

import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.filter.KeycloakPreAuthActionsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.filter.CorsFilter;

@KeycloakConfiguration
@EnableWebSecurity
class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {
    private final CORSFilter corsFilter;

    public SecurityConfig(CORSFilter corsFilter) {
        this.corsFilter = corsFilter;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        var keycloakAuthenticationProvider = keycloakAuthenticationProvider();
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
        auth.authenticationProvider(keycloakAuthenticationProvider);
    }

    @Bean
    @Override
    protected KeycloakPreAuthActionsFilter keycloakPreAuthActionsFilter() {
        return new CustomKeycloakPreAuthActionsFilter(httpSessionManager());
    }

    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http
                .csrf()
                .disable().addFilterAfter(corsFilter, CorsFilter.class)
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .antMatchers("/swagger/**", "/swagger-ui/**", "/swagger-ui.html", "/webjars/**",
                                "/swagger-resources/**", "/configuration/**", "/v3/api-docs/**", "/actuator/**")
                        .permitAll()
                        .anyRequest()
                        .permitAll());
    }
}
