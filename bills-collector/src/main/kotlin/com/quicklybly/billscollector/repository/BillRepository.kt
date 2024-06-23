package com.quicklybly.billscollector.repository

import com.quicklybly.billscollector.entity.Bill
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface BillRepository : JpaRepository<Bill, Long> {

    @Query(
        "select b from Bill b where b.name = :name and b.client.id = :clientId"
    )
    fun findByNameAndClientId(
        @Param("name") name: String,
        @Param("clientId") clientId: Long
    ): Bill?

    @Query(
        "select b from Bill b where b.client.id = :clientId"
    )
    fun findAllByClientId(clientId: Long): List<Bill>
}
