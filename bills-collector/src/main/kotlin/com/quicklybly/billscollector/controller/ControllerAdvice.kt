package com.quicklybly.billscollector.controller

import com.quicklybly.billscollector.exception.AppException
import com.quicklybly.billscollector.model.ErrorDto
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(AppException::class)
    fun handleAppException(ex: AppException): ResponseEntity<ErrorDto> {
        logger.info(ex.message)

        return ResponseEntity
            .status(ex.httpCode)
            .body(
                ErrorDto(ex.message)
            )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ErrorDto> {
        val msg = ex.message ?: "Unknown error"

        logger.error(msg, ex)

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ErrorDto(msg)
            )
    }

    companion object {
        private val logger = KotlinLogging.logger {}
    }
}
