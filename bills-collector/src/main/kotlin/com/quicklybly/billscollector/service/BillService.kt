package com.quicklybly.billscollector.service

import com.quicklybly.billscollector.entity.Bill
import com.quicklybly.billscollector.exception.AppException
import com.quicklybly.billscollector.exception.BillNotFoundException
import com.quicklybly.billscollector.exception.PermissionDeniedException
import com.quicklybly.billscollector.mapper.BillMapper
import com.quicklybly.billscollector.model.BillDto
import com.quicklybly.billscollector.repository.BillRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BillService(
    private val repository: BillRepository,
    private val mapper: BillMapper,
    private val authService: AuthenticationService,
) {

    @Transactional
    fun createBill(dto: BillDto): BillDto {
        val client = authService.extractClient()

        repository.findByNameAndClientId(dto.name, client.id!!)?.let {
            throw AppException("Bill with name '${dto.name}' already exists", HttpStatus.CONFLICT)
        }

        val entity = repository.save(
            mapper.toEntity(dto).also {
                it.client = client
            }
        )

        return mapper.toDto(entity)
    }

    @Transactional(readOnly = true)
    fun getBills(): List<BillDto> {
        val client = authService.extractClient()

        return repository.findAllByClientId(client.id!!).map { mapper.toDto(it) }
    }

    @Transactional
    fun updateBill(id: Long, dto: BillDto): BillDto {
        val client = authService.extractClient()

        val bill: Bill = repository.findById(id).orElseThrow {
            BillNotFoundException(id)
        }

        if (bill.client?.id != client.id) {
            throw PermissionDeniedException(id)
        }

        bill.name = dto.name
        bill.description = dto.description

        return mapper.toDto(bill)
    }

    @Transactional
    fun deleteBill(id: Long) {
        val client = authService.extractClient()

        val bill: Bill? = repository.findById(id).orElse(null)

        bill?.let {
            if (it.client?.id != client.id) {
                throw PermissionDeniedException(id)
            }
            repository.delete(it)
        }
    }
}
