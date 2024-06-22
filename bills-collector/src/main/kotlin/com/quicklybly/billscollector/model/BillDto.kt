package com.quicklybly.billscollector.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class BillDto(

    @field:JsonProperty("id")
    val id: Long? = null,

    @field:NotNull
    @field:Size(
        min = 1,
        max = 255
    )
    @field:NotEmpty
    @field:NotBlank
    @field:JsonProperty("name")
    val name: String,

    @field:Size(max = 255)
    @field:JsonProperty("description")
    val description: String? = null,
)
