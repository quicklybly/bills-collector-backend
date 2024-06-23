package com.quicklybly.billscollector.service

import com.quicklybly.billscollector.exception.BillNotFoundException
import com.quicklybly.billscollector.exception.PermissionDeniedException
import com.quicklybly.billscollector.model.AdviceRequest
import com.quicklybly.billscollector.model.AdviceRequestUsage
import com.quicklybly.billscollector.repository.BillRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdviceService(
    private val billRepository: BillRepository,
    private val chatService: ChatService,
    private val authService: AuthenticationService,
) {

    @Transactional(readOnly = true)
    fun receiveAdvice(billId: Long): String {
        val client = authService.extractClient()

        val bill = billRepository.findById(billId).orElseThrow {
            BillNotFoundException(billId)
        }

        if (bill.client?.id != client.id) {
            throw PermissionDeniedException(billId)
        }

        val adviceRequest = AdviceRequest(
            name = bill.name!!,
            description = bill.description!!,
            usages = bill.usages.map {
                AdviceRequestUsage(
                    it.countDate!!,
                    it.usage!!,
                )
            }
        )

        return chatService.getAdvice(adviceRequest)
    }
}