package com.innouti.mercadolivro.exception

import com.innouti.mercadolivro.controller.response.ErrorResponse
import com.innouti.mercadolivro.controller.response.FieldErrorResponse
import com.innouti.mercadolivro.enums.Errors
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@org.springframework.web.bind.annotation.ControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundExceptionException(ex: NotFoundException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val httpStatus = HttpStatus.NOT_FOUND
        val error = ErrorResponse(
            httpStatus.value(),
            ex.message,
            ex.errorCode,
            null
        )
        return ResponseEntity(error, httpStatus)
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: BadRequestException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val httpStatus = HttpStatus.BAD_REQUEST
        val error = ErrorResponse(
            httpStatus.value(),
            ex.message,
            ex.errorCode,
            null
        )
        return ResponseEntity(error, httpStatus)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidExceptionException(
        ex: MethodArgumentNotValidException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        val httpStatus = HttpStatus.UNPROCESSABLE_ENTITY
        val error = ErrorResponse(
            httpStatus.value(),
            Errors.ML001.message,
            Errors.ML001.code,
            ex.bindingResult.fieldErrors.map {
                FieldErrorResponse(it.defaultMessage ?: "Invalid", it.field)
            }
        )
        return ResponseEntity(error, httpStatus)
    }

    @ExceptionHandler(PurchaseBookException::class)
    fun handlePurchaseBookException(
        ex: PurchaseBookException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        val httpStatus = HttpStatus.BAD_REQUEST
        val error = ErrorResponse(
            httpStatus.value(),
            ex.message,
            ex.errorCode,
            null
        )
        return ResponseEntity(error, httpStatus)
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException::class)
    fun handlePurchaseBookException(
        ex: org.springframework.security.access.AccessDeniedException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        val httpStatus = HttpStatus.FORBIDDEN
        val error = ErrorResponse(
            httpStatus.value(),
            Errors.ML000.message,
            Errors.ML000.code,
            null
        )
        return ResponseEntity(error, httpStatus)
    }
}