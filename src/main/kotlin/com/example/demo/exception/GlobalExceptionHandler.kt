package com.example.demo.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException::class)
    fun handleStudentNotFoundException(exception: NotFoundException): ResponseEntity<Any> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(exception.message)
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(exception: RuntimeException): ResponseEntity<Any> {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(exception.message)
    }
}
