package com.quicklybly.billscollector.entity

import jakarta.persistence.*

@Entity
@Table(
    name = "bill",
    indexes = [
        Index(name = "bill_name_idx", columnList = "name"),
        Index(name = "bill_client_id_idx", columnList = "client_id"),
    ]
)
open class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @Column(name = "name", nullable = false)
    open var name: String? = null

    @Column(name = "description", nullable = true)
    open var description: String? = null

    @ManyToOne(optional = false)
    open var client: Client? = null

    @OneToMany(
        mappedBy = "bill",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
    )
    open var usages: MutableList<BillUsage> = mutableListOf()
}
