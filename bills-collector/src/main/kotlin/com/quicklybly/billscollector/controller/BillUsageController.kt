package com.quicklybly.billscollector.controller

import com.quicklybly.billscollector.model.BillDto
import com.quicklybly.billscollector.model.BillUsageDto
import com.quicklybly.billscollector.model.ErrorDto
import com.quicklybly.billscollector.service.BillUsageService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("bills/{bill-id}/usages")
class BillUsageController(
    private val service: BillUsageService,
) {

    @Operation(
        summary = "Get all usages for bill",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Usages retrieved",
                content = [
                    Content(array = ArraySchema(schema = Schema(implementation = BillUsageDto::class)))
                ]
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
                description = "Bill Not Found",
                content = [Content(schema = Schema(implementation = ErrorDto::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Internal Server Error",
                content = [Content(schema = Schema(implementation = ErrorDto::class))]
            ),
        ]
    )
    @GetMapping
    fun getUsages(@PathVariable("bill-id") billId: Long): List<BillUsageDto> = service.getUsages(billId)

    @Operation(
        summary = "Create usage",
        responses = [
            ApiResponse(
                responseCode = "201",
                description = "Usage successfully created",
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
                responseCode = "500",
                description = "Internal Server Error",
                content = [Content(schema = Schema(implementation = ErrorDto::class))]
            ),
        ]
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createUsage(
        @PathVariable("bill-id") billId: Long,
        @Valid @RequestBody dto: BillUsageDto
    ): BillUsageDto = service.createUsage(billId, dto)

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
                description = "Usage not found",
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
    fun updateUsage(
        @PathVariable("bill-id") billId: Long,
        @PathVariable("id") id: Long,
        @Valid @RequestBody dto: BillUsageDto
    ): BillUsageDto = service.updateUsage(billId, id, dto)

    @Operation(
        summary = "Delete usage",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Usage successfully deleted",
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
                description = "Usage not found",
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
    fun deleteUsage(
        @PathVariable("bill-id") billId: Long,
        @PathVariable("id") id: Long,
    ) = service.deleteUsage(billId, id)
}
