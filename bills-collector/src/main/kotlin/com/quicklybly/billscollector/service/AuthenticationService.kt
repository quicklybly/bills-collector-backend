package com.quicklybly.billscollector.service

import com.quicklybly.billscollector.entity.AppClient
import com.quicklybly.billscollector.repository.AppClientRepository
import jakarta.transaction.Transactional
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val repository: AppClientRepository,
) {

    @Transactional
    fun extractClient(): AppClient {
        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as Jwt
        val username = principal.claims["preferred_username"].toString()

        val client: AppClient = repository.findAppClientByUsername(username)
            ?: repository.save(
                AppClient().also {
                    it.username = username
                }
            )

        return client
    }
}
