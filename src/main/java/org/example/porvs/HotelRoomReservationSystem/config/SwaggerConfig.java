package org.example.porvs.HotelRoomReservationSystem.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi priceMatrixApi() {
        var securitySchemeNamePerseus = "clientOAuth2";
        return GroupedOpenApi
                .builder()
                .group("Client")
                .pathsToMatch("/api/v*/client/**")
                .addOpenApiCustomiser(openApi -> {
                    var brokenTags = "client";
                    openApi.getTags().removeIf(tag -> brokenTags.contains(tag.getName()));
                    openApi
                            .getPaths()
                            .forEach((k, v) -> v
                                    .readOperations()
                                    .forEach(operation -> operation.getTags().removeIf(brokenTags::contains)));
                })
                .build();
    }

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI().servers(List.of(new Server().url("/"))).components(new Components()).info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("Hotel Room Reservation System")
                .description("Система бронирования номеров в отеле")
                .contact(new Contact().name("Андрей Калинин").email("andrei@mail.com"))
                .version("1.0.0");
    }
}
