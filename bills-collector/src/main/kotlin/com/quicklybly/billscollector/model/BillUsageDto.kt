package com.quicklybly.billscollector.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero
import java.math.BigDecimal
import java.time.LocalDate

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class BillUsageDto(

    @field:JsonProperty("id")
    val id: Long? = null,

    @field:NotNull
    @field:JsonProperty("count_date")
    val countDate: LocalDate,

    @field:NotNull
    @field:PositiveOrZero
    @field:JsonProperty("usage")
    val usage: BigDecimal,

    @field:JsonProperty("bill_id")
    val billId: Long,
)
