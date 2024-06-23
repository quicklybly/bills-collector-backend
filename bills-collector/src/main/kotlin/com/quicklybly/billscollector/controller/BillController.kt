package com.quicklybly.billscollector.controller

import com.quicklybly.billscollector.model.BillDto
import com.quicklybly.billscollector.model.ErrorDto
import com.quicklybly.billscollector.service.BillService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("bills")
class BillController(
    private val service: BillService,
) {

    @Operation(
        summary = "Get all bills",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Bills retrieved",
                content = [
                    Content(array = ArraySchema(schema = Schema(implementation = BillDto::class)))
                ]
            ),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized",
            ),
            ApiResponse(
                responseCode = "500",
                description = "Internal Server Error",
                content = [Content(schema = Schema(implementation = ErrorDto::class))]
            ),
        ]
    )
    @GetMapping
    fun getBills(): List<BillDto> = service.getBills()

    @Operation(
        summary = "Create bill",
        responses = [
            ApiResponse(
                responseCode = "201",
                description = "Bill successfully created",
                content = [Content(schema = Schema(implementation = BillDto::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = [Content(schema = Schema(implementation = String::class))]
            ),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized",
            ),
            ApiResponse(
                responseCode = "409",
                description = "Bills already exists",
                content = [Content(schema = Schema(implementation = ErrorDto::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Internal Server Error",
                content = [Content(schema = Schema(implementation = ErrorDto::class))]
            ),
        ]
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createBill(@Valid @RequestBody dto: BillDto): BillDto = service.createBill(dto)

    @Operation(
        summary = "Update bill",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Bill successfully updated",
                content = [Content(schema = Schema(implementation = BillDto::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = [Content(schema = Schema(implementation = String::class))]
            ),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized",
            ),
            ApiResponse(
                responseCode = "403",
                description = "Permission denied",
                content = [Content(schema = Schema(implementation = ErrorDto::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Bill not found",
                content = [Content(schema = Schema(implementation = ErrorDto::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Internal Server Error",
                content = [Content(schema = Schema(implementation = ErrorDto::class))]
            ),
        ]
    )
    @PutMapping("/{id}")
    fun updateBill(@PathVariable("id") id: Long, @Valid @RequestBody dto: BillDto): BillDto =
        service.updateBill(id, dto)

    @Operation(
        summary = "Delete bill",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Bill successfully deleted",
            ),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized",
            ),
            ApiResponse(
                responseCode = "403",
                description = "Permission denied",
                content = [Content(schema = Schema(implementation = ErrorDto::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Internal Server Error",
                content = [Content(schema = Schema(implementation = ErrorDto::class))]
            ),
        ]
    )
    @DeleteMapping("/{id}")
    fun deleteBill(@PathVariable("id") id: Long) = service.deleteBill(id)
}
