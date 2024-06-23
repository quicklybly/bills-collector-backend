package com.quicklybly.billscollector.mapper

import com.quicklybly.billscollector.entity.BillUsage
import com.quicklybly.billscollector.model.BillUsageDto
import org.springframework.stereotype.Component

fun BillUsage.mergeWithDto(dto: BillUsageDto): BillUsage = this.also {
    it.countDate = dto.countDate
    it.usage = dto.usage
}

@Component
class BillUsageMapper {

    // very safe code ^_^
    fun toDto(entity: BillUsage): BillUsageDto = BillUsageDto(
        id = entity.id!!,
        countDate = entity.countDate!!,
        usage = entity.usage!!,
        billId = entity.bill?.id!!,
    )

    fun toEntity(dto: BillUsageDto): BillUsage = BillUsage().also {
        it.id = dto.id
        it.countDate = dto.countDate
        it.usage = dto.usage
    }
}
