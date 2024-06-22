package com.quicklybly.billscollector.service

import com.quicklybly.billscollector.entity.Client
import com.quicklybly.billscollector.repository.ClientRepository
import jakarta.transaction.Transactional
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val repository: ClientRepository,
) {

    @Transactional
    fun extractClient(): Client {
        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as Jwt
        val username = principal.claims["preferred_username"].toString()

        val client: Client = repository.findClientByUsername(username)
            ?: repository.save(
                Client().also {
                    it.username = username
                }
            )

        return client
    }
}
