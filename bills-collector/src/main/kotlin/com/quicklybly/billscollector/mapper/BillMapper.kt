package com.quicklybly.billscollector.mapper

import com.quicklybly.billscollector.entity.Bill
import com.quicklybly.billscollector.model.BillDto
import org.springframework.stereotype.Component

@Component
class BillMapper {

    // very safe code ^_^
    fun toDto(entity: Bill): BillDto = BillDto(
        id = entity.id!!,
        name = entity.name!!,
        description = entity.description,
    )

    fun toEntity(dto: BillDto): Bill = Bill().also {
        it.id = dto.id
        it.name = dto.name
        it.description = dto.description
    }
}
