package com.quicklybly.billscollector.exception

import org.springframework.http.HttpStatus

open class AppException(
    override val message: String = "Internal server error",
    val httpCode: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
) : RuntimeException(message)

class BillNotFoundException(id: Long) : AppException("Bill '$id' not found", HttpStatus.NOT_FOUND)

class UsageNotFoundException(id: Long) : AppException("Usage '$id' not found", HttpStatus.NOT_FOUND)

class UpdateBillUsageBadRequestException(parentBillId: Long, requestedBillId: Long) : AppException(
    "Parent bill doesn't match requested bill: parentBillId='$parentBillId', requestedBillId='$requestedBillId'",
    HttpStatus.BAD_REQUEST
)

class PermissionDeniedException(id: Long) : AppException("Permission denied for bill '$id'", HttpStatus.FORBIDDEN)
