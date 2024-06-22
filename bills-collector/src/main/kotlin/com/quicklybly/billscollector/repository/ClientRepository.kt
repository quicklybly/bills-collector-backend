package com.quicklybly.billscollector.repository

import com.quicklybly.billscollector.entity.Client
import org.springframework.data.jpa.repository.JpaRepository

interface ClientRepository : JpaRepository<Client, Long> {

    fun findClientByUsername(username: String): Client?
}
