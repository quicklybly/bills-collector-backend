package com.quicklybly.billscollector.service

import com.quicklybly.billscollector.entity.Bill
import com.quicklybly.billscollector.entity.BillUsage
import com.quicklybly.billscollector.exception.BillNotFoundException
import com.quicklybly.billscollector.exception.PermissionDeniedException
import com.quicklybly.billscollector.exception.UpdateBillUsageBadRequestException
import com.quicklybly.billscollector.exception.UsageNotFoundException
import com.quicklybly.billscollector.mapper.BillUsageMapper
import com.quicklybly.billscollector.mapper.mergeWithDto
import com.quicklybly.billscollector.model.BillUsageDto
import com.quicklybly.billscollector.repository.BillRepository
import com.quicklybly.billscollector.repository.BillUsageRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BillUsageService(
    private val usageRepository: BillUsageRepository,
    private val billRepository: BillRepository,
    private val mapper: BillUsageMapper,
    private val authService: AuthenticationService,
) {

    @Transactional(readOnly = true)
    fun getUsages(billId: Long): List<BillUsageDto> {
        val bill = getValidatedBill(billId)

        return bill.usages.map { mapper.toDto(it) }
    }

    @Transactional
    fun createUsage(billId: Long, usageDto: BillUsageDto): BillUsageDto {
        val bill = getValidatedBill(billId)

        val usage = mapper.toEntity(usageDto).also {
            it.bill = bill
        }

        val persistedUsage = usageRepository.save(usage)

        return mapper.toDto(persistedUsage)
    }

    @Transactional
    fun updateUsage(
        billId: Long,
        id: Long,
        usageDto: BillUsageDto
    ): BillUsageDto {
        val bill = getValidatedBill(billId)

        val usage = usageRepository.findById(id).orElseThrow {
            UsageNotFoundException(id)
        }

        if (bill.id != usage.bill?.id) {
            throw UpdateBillUsageBadRequestException(usage.bill?.id!!, bill.id!!)
        }

        val persistedUsage = usageRepository.save(usage.mergeWithDto(usageDto))

        return mapper.toDto(persistedUsage)
    }

    @Transactional
    fun deleteUsage(
        billId: Long,
        id: Long,
    ) {
        val bill = getValidatedBill(billId)

        val usage: BillUsage? = usageRepository.findById(id).orElse(null)

        usage?.let {
            if (bill.id != usage.bill?.id) {
                throw UpdateBillUsageBadRequestException(usage.bill?.id!!, bill.id!!)
            }

            usageRepository.deleteById(usage.id!!)
        }
    }

    private fun getValidatedBill(id: Long): Bill {
        val client = authService.extractClient()

        val bill = billRepository.findById(id).orElseThrow {
            BillNotFoundException(id)
        }

        if (bill.client?.id != client.id) {
            throw PermissionDeniedException(id)
        }

        return bill
    }
}
