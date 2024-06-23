package com.quicklybly.billscollector.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate

@Entity
@Table(
    name = "bill_usage",
    indexes = [
        Index(name = "bill_usage_bill_id_idx", columnList = "bill_id"),
    ]
)
open class BillUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @Column(name = "count_date", nullable = false)
    open var countDate: LocalDate? = null

    @Column(name = "usage", nullable = false, precision = 19, scale = 2)
    open var usage: BigDecimal? = null

    @ManyToOne(optional = false)
    @JoinColumn(name = "bill_id", nullable = false)
    open var bill: Bill? = null
}
