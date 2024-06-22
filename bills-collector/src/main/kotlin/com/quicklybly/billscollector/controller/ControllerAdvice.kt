package com.quicklybly.billscollector.controller

import com.quicklybly.billscollector.exception.AppException
import com.quicklybly.billscollector.model.ErrorDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(AppException::class)
    fun handleAppException(ex: AppException) = ResponseEntity
        .status(ex.httpCode)
        .body(
            ErrorDto(ex.message)
        )

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception) = ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(
            ErrorDto(ex.message ?: "Unknown error")
        )
}
