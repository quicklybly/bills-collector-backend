package com.quicklybly.billscollector.entity

import jakarta.persistence.*

@Entity
@Table(name = "bill")
open class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @Column(name = "name", nullable = false)
    open var name: String? = null

    @Column(name = "description", nullable = true)
    open var description: String? = null
}