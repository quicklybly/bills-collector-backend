package com.quicklybly.billscollector.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.time.LocalDate

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class AdviceRequest(

    @field:JsonProperty("name")
    val name: String,

    @field:JsonProperty("description")
    val description: String? = null,

    @field:JsonProperty("usages")
    val usages: List<AdviceRequestUsage>,
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class AdviceRequestUsage(

    @field:JsonProperty("count_date")
    val countDate: LocalDate,

    @field:JsonProperty("usage")
    val usage: BigDecimal,
)
