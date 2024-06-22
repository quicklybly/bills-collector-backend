package com.quicklybly.billscollector.repository

import com.quicklybly.billscollector.entity.AppClient
import org.springframework.data.jpa.repository.JpaRepository

interface AppClientRepository : JpaRepository<AppClient, Long> {

    fun findAppClientByUsername(username: String): AppClient?
}
