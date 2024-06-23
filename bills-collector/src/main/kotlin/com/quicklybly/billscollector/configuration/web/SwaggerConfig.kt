package com.quicklybly.billscollector.configuration.web

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.OAuthFlow
import io.swagger.v3.oas.models.security.OAuthFlows
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private const val OAUTH_SCHEME_NAME: String = "keycloak"

@Configuration
class SwaggerConfig {

    @Value("\${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    lateinit var authServerUrl: String

    val realm: String = "bills-collector"

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
        .components(
            Components()
                .addSecuritySchemes(OAUTH_SCHEME_NAME, schema())
        )
        .addSecurityItem(SecurityRequirement().addList(OAUTH_SCHEME_NAME))

    private fun schema(): SecurityScheme = SecurityScheme()
        .type(SecurityScheme.Type.OAUTH2)
        .flows(
            OAuthFlows()
                .password(
                    OAuthFlow()
                        .tokenUrl("$authServerUrl/realms/$realm/protocol/openid-connect/token")
                        .refreshUrl("$authServerUrl/realms/$realm/protocol/openid-connect/token")
                )
        )
}
