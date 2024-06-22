package com.quicklybly.billscollector.exception

import org.springframework.http.HttpStatus

class AppException(
    override val message: String = "Internal server error",
    val httpCode: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
) : RuntimeException(message)
