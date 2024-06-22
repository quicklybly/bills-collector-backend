package com.quicklybly.billscollector.configuration.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
@Configuration
class SecurityConfig {

    @Bean
    fun filterChain(
        http: HttpSecurity,
    ): SecurityFilterChain {
        http
            .cors { it.disable() }
            .csrf { it.disable() }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.NEVER)
            }
            .authorizeHttpRequests {
                it.requestMatchers("/swagger-ui/**").permitAll()
                it.requestMatchers("/v3/api-docs/**").permitAll()
                it.anyRequest().authenticated()
            }
            .oauth2ResourceServer { oauth2Configurer ->
                oauth2Configurer.jwt { jwtConfigurer ->
                    jwtConfigurer.jwtAuthenticationConverter { jwt ->
                        val realmAccess: Map<String, Collection<String>> = jwt.getClaim("realm_access")
                        val roles: Collection<String>? = realmAccess["roles"]
                        val authorities = roles
                            ?.map { SimpleGrantedAuthority("ROLE_$it") } ?: emptyList()
                        JwtAuthenticationToken(jwt, authorities)
                    }
                }
            }
        return http.build()
    }
}
