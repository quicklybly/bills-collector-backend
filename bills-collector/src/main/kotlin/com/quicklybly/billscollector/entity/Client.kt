package com.quicklybly.billscollector.entity

import jakarta.persistence.*

@Entity
@Table(
    name = "client",
    indexes = [
        Index(name = "client_username_idx", columnList = "username")
    ]
)
open class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @Column(name = "username", nullable = false, unique = true)
    open var username: String? = null
}
