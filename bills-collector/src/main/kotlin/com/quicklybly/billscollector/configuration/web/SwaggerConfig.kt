package com.quicklybly.billscollector.configuration.web

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun openApi(): OpenAPI = OpenAPI()
        .info(
            Info()
                .title("Bills Collector API")
                .description("The best bills service!")
                .contact(
                    Contact()
                        .name("Artem Lysenko")
                        .url("https://t.me/quicklybly")
                )
                .version("v0.0.1")
        )
        .servers(
            listOf(
                Server()
                    .url("http://localhost:8080/bills-collector")
                    .description("Local development server"),
                Server()
                    .url("http://213.171.3.55/api/bills-collector")
                    .description("Production server"),
                Server()
                    .url("http://213.171.3.55:8080/bills-collector")
                    .description("Direct production server")
            )
        )
        .components(Components())
}
