package com.quicklybly.billscollector.controller

import com.quicklybly.billscollector.model.BillDto
import com.quicklybly.billscollector.service.BillService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("bills")
class BillController(
    private val service: BillService,
) {

    @GetMapping
    fun getBills(): List<BillDto> = service.getBills()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createBill(@Valid @RequestBody dto: BillDto): BillDto = service.createBill(dto)

    @PutMapping("/{id}")
    fun updateBill(@PathVariable("id") id: Long, @Valid @RequestBody dto: BillDto): BillDto =
        service.updateBill(id, dto)

    @DeleteMapping("/{id}")
    fun deleteBill(@PathVariable("id") id: Long) = service.deleteBill(id)
}
