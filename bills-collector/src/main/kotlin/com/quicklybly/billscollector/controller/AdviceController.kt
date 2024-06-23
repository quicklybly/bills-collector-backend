package com.quicklybly.billscollector.controller

import com.quicklybly.billscollector.service.AdviceService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("bills/{bill-id}/advice")
class AdviceController(
    private val service: AdviceService,
) {

    @GetMapping
    fun receiveAdvice(
        @PathVariable("bill-id") billId: Long
    ): String = service.receiveAdvice(billId)
}
